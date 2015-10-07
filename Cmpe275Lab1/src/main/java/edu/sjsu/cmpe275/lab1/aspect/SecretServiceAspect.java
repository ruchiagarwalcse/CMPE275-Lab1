package edu.sjsu.cmpe275.lab1.aspect;

import java.util.UUID;

import edu.sjsu.cmpe275.lab1.model.Secret;
import edu.sjsu.cmpe275.lab1.service.SecretServiceImpl;
import edu.sjsu.cmpe275.lab1.exception.UnauthorizedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * SecretServiceAspect
 * Created on September 29th, 2015
 * @author Ruchi Agarwal
 */
@Aspect
public class SecretServiceAspect {

    /**
     * Log message after secret is created and stored.
     * @param joinPoint contains the context info of the execution point.
     */
    @After("execution(public java.util.UUID edu.sjsu.cmpe275.lab1.service.SecretService.storeSecret(..))")
    public void afterStoreSecret(JoinPoint joinPoint) {
        String userId = joinPoint.getArgs()[0].toString();
        Secret secret = (Secret) joinPoint.getArgs()[1];
        UUID secretId = secret.getSecretIds().get(secret.getSecretIds().size() - 1);
        System.out.println(userId + " creates a secret with ID "+ secretId);
    }

    /**
     * Log message before secret is read & check if user is authorized to read the secret.
     * @param joinPoint contains the context info of the execution point.
     * @throws edu.sjsu.cmpe275.lab1.exception.UnauthorizedException if user is not authorized to read the secret.
     */
    @Before("execution(public edu.sjsu.cmpe275.lab1.model.Secret edu.sjsu.cmpe275.lab1.service.SecretService.readSecret(..))")
    public void beforeReadSecret(JoinPoint joinPoint) {
        String userId = joinPoint.getArgs()[0].toString();
        UUID secretId = (UUID) joinPoint.getArgs()[1];
        Secret secret = SecretServiceImpl.getSecrets().get(secretId);
        System.out.println(userId + " reads the secret ID "+ secretId);
        if ((! secret.getUsers().contains(userId)) && (!secret.getOwner().equals(userId))) {
            String message;
            if (secret == null) {
                message = "Throw Exception :" + secretId + " does not exist ";
            } else {
                message = "Throw Exception :" + userId + " does not have permission to read secret of ID  " + secretId;
            }
            System.out.println(message);
            throw new UnauthorizedException();
        }
    }

    /**
     * Log message before secret is shared & check if user is authorized to share the secret.
     * @param joinPoint contains the context info of the execution point.
     * @throws UnauthorizedException if user is not authorized to share the secret.
     */
    @Before("execution(public void edu.sjsu.cmpe275.lab1.service.SecretService.shareSecret(..))")
    public void beforeShareSecret(JoinPoint joinPoint) {
        String userId = joinPoint.getArgs()[0].toString();
        UUID secretId = (UUID) joinPoint.getArgs()[1];
        String targetUserId =  joinPoint.getArgs()[2].toString();
        Secret secret =  SecretServiceImpl.getSecrets().get(secretId);
        System.out.println(userId+" shares the secret of ID "+ secretId + " with "+ targetUserId);
        if ((!secret.getUsers().contains(userId)) && (!secret.getOwner().equals(userId))) {
            System.out.println(userId + " is not authorized to share secret of ID "+ secretId + " with "+ targetUserId);
            throw new UnauthorizedException();
        }
    }

    /**
     * Log message before secret is unshared & check if user is authorized to unshare the secret.
     * @param joinPoint contains the context info of the execution point.
     * @throws UnauthorizedException if user is not authorized to unshare the secret.
     */
    @Before("execution(public void edu.sjsu.cmpe275.lab1.service.SecretService.unshareSecret(..))")
    public void beforeUnshareSecret(JoinPoint joinPoint) {
        String userId = joinPoint.getArgs()[0].toString();
        UUID secretId = (UUID) joinPoint.getArgs()[1];
        String targetUserId =  joinPoint.getArgs()[2].toString();
        Secret secret =  SecretServiceImpl.getSecrets().get(secretId);
        System.out.println(userId + " unshares the secret of ID "+ secretId + " with "+ targetUserId);
        if (secret != null && (!(secret.getUsers().contains(userId) || secret.getOwner().equals(userId)))) {
            System.out.println(userId+" is not authorized to unshare secret of ID "+ secretId + " with "+ targetUserId);
            throw new UnauthorizedException();
        }
    }
}
