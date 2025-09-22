package br.com.jstack.org.catalog.graph.framework.adapter.exception;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import br.com.jstack.org.catalog.graph.model.StatusErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
	
	private static final MediaType PROBLEM_JSON   = MediaType.valueOf("application/problem+json");
	private static final String    HDR_REQUEST_ID = "X-Request-Id";
	private static final String    ERR_BASE       = "https://errors.jstack.com.br/catalog";
	
	// 422 — body inválido (Bean Validation)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StatusErrorResponse> handleValidationBody(MethodArgumentNotValidException ex,
	                                                                HttpServletRequest req) {
		String detail = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(f -> f.getField() + ": " + defaultMsg(f.getDefaultMessage()))
			.collect(Collectors.joining("; "));
		
		return buildErrorResponse(
			req, HttpStatus.UNPROCESSABLE_ENTITY,
			ERR_BASE + "/validation",
			"Unprocessable Entity",
			detail
		);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StatusErrorResponse> handleValidationParam(ConstraintViolationException ex, HttpServletRequest req) {
		String detail = ex.getConstraintViolations()
			.stream()
			.map(v -> v.getPropertyPath() + ": " + defaultMsg(v.getMessage()))
			.collect(Collectors.joining("; "));
		
		return buildErrorResponse(
			req, HttpStatus.UNPROCESSABLE_ENTITY,
			ERR_BASE + "/validation",
			"Unprocessable Entity",
			detail
		);
	}
	
	// 400 — tipo de parâmetro inválido
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<StatusErrorResponse> handleParamMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
		String expected = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido";
		String detail   = "Parâmetro '" + ex.getName() + "' deve ser do tipo '" + expected + "'";
		return buildErrorResponse(
			req, HttpStatus.BAD_REQUEST,
			ERR_BASE + "/parameter-type-mismatch",
			"Bad Request",
			detail
		);
	}
	
	// 404 — não encontrado
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<StatusErrorResponse> handleNotFound(NoSuchElementException ex, HttpServletRequest req) {
		return buildErrorResponse(
			req, HttpStatus.NOT_FOUND,
			ERR_BASE + "/not-found",
			"Not Found",
			defaultMsg(ex.getMessage())
		);
	}
	
	// 409 — violação de regra de negócio
	@ExceptionHandler(DomainRuleViolation.class)
	public ResponseEntity<StatusErrorResponse> handleDomainRuleViolation(DomainRuleViolation ex, HttpServletRequest req) {
		
		// Se sua exceção tiver um código/slug, pode compor o "type" com ele
		String type   = ERR_BASE + "/domain-rule-violation";
		String detail = defaultMsg(ex.getMessage());
		return buildErrorResponse(req, HttpStatus.CONFLICT, type, "Conflict", detail);
	}
	
	// 400 — argumentos ilegais
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StatusErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest req) {
		return buildErrorResponse(
			req, HttpStatus.BAD_REQUEST,
			ERR_BASE + "/illegal-argument",
			"Bad Request",
			defaultMsg(ex.getMessage())
		);
	}
	
	// 500 — fallback
	@ExceptionHandler(Exception.class)
	public ResponseEntity<StatusErrorResponse> handleUnexpected(Exception ex, HttpServletRequest req) {
		log.error("Unhandled exception", ex);
		return buildErrorResponse(
			req, HttpStatus.INTERNAL_SERVER_ERROR,
			ERR_BASE + "/unexpected",
			"Internal Server Error",
			"Unexpected error: " + defaultMsg(ex.getMessage())
		);
	}
	
	private ResponseEntity<StatusErrorResponse> buildErrorResponse(HttpServletRequest req, HttpStatus status, String typeUri, String title, String detail) {
		
		String instance  = req.getRequestURI();
		String requestId = resolveOrGenerateRequestId(req);
		
		StatusErrorResponse statusError = new StatusErrorResponse();
		statusError.type(URI.create(typeUri));
		statusError.title(title);
		statusError.status(status.value());
		statusError.detail(detail);
		statusError.instance(instance);
		statusError.requestId(requestId);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(PROBLEM_JSON);
		headers.set(HDR_REQUEST_ID, requestId);
		
		return new ResponseEntity<>(statusError, headers, status);
	}
	
	private String resolveOrGenerateRequestId(HttpServletRequest req) {
		String rid = req.getHeader(HDR_REQUEST_ID);
		return StringUtils.hasText(rid) ? rid : UUID.randomUUID().toString();
	}
	
	private String defaultMsg(String msg) {
		return (msg == null || msg.isBlank()) ? "no detail" : msg;
	}
}