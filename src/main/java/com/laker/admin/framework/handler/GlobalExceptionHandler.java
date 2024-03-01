
package com.laker.admin.framework.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import cn.hutool.core.lang.Dict;
import com.laker.admin.framework.aop.ratelimit.RateLimitException;
import com.laker.admin.framework.exception.BusinessException;
import com.laker.admin.framework.model.Response;
import com.laker.admin.utils.http.HttpServletRequestUtil;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public Response handleRRException(BusinessException e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error("业务异常", e.getMsg());
        return Response.error(e.getCode() + "", e.getMsg());
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Response handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error("方法参数类型不匹配", e);
        return Response.error("方法参数类型不匹配");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error("缺少请求参数", e);
        return Response.error("缺少请求参数");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error("参数解析失败", e);
        return Response.error("参数解析失败");
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Response handleValidationException(ValidationException e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error("参数验证失败", e);
        return Response.error("参数验证失败");
    }

    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error("不支持当前请求方法", e);
        return Response.error("不支持当前请求方法");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Response handleHttpMediaTypeNotSupportedException(Exception e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error("不支持当前媒体类型", e);
        return Response.error("不支持当前媒体类型");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Response handleDuplicateKeyException(DuplicateKeyException e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error(e.getMessage(), e);
        return Response.error("500", "数据库中已存在该记录");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Response handleMaxSizeException(MaxUploadSizeExceededException e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error(e.getMessage(), e);
        return Response.error("500", "File too large!");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public Response handleNotLoginException(NotLoginException e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error(e.getMessage());
        return Response.error("401", e.getMessage());
    }

    @ExceptionHandler(SaTokenException.class)
    public Response handleMaxSizeException(SaTokenException e) {
        log.error("uri：{}, httpMethod:{}, errMsg:{}", HttpServletRequestUtil.getRequestURI(), HttpServletRequestUtil.getRequest().getMethod(), e);
        return Response.error("403", e.getMessage());
    }

    /**
     * 验证bean类型
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<Map> result = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            result.add(Dict.create().set("field", fieldError.getField()).set("msg", fieldError.getDefaultMessage()));
        });
        return Response.error(result);
    }

    /**
     * 验证参数param类型
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<List<Map>> handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        List<Map> result = new ArrayList<>();
        e.getConstraintViolations().forEach((constraintViolation) -> {
            PathImpl path = (PathImpl) constraintViolation.getPropertyPath();
            NodeImpl leafNode = path.getLeafNode();
            String leafNodeName = leafNode.getName();
            result.add(Dict.create().set("field", leafNodeName).set("msg", constraintViolation.getMessage()));
        });
        return Response.error(result);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public Response<Void> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return Response.error("404", "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(RateLimitException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Response<Void> handleRateLimitException(RateLimitException e) {
        log.error(e.getMessage(), e);
        return Response.error("429", "请求过于频繁，请稍后重试");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Void> handleException(Exception e) {
        log.info(HttpServletRequestUtil.getAllRequestInfo());
        log.error(e.getMessage(), e);
        return Response.error("500", "服务器异常");
    }
}
