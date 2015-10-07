package edu.sjsu.cmpe275.lab1.tests;

import static org.junit.Assert.*;

import edu.sjsu.cmpe275.lab1.model.Secret;
import edu.sjsu.cmpe275.lab1.exception.UnauthorizedException;
import edu.sjsu.cmpe275.lab1.service.SecretService;
import org.junit.Test;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

/**
 * SecretServiceTest
 * Created on September 29th, 2015
 * @author Ruchi Agarwal
 */
public class SecretServiceTest {

    /** Secret service object. */
    private SecretService secretServices;

    /** Test set up. */
    @Before
    public void setUp() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        secretServices = context.getBean("secretService", SecretService.class);
    }

    /**
     * TestA: Bob cannot read Alice’s secret, which has not been shared with Bob.
     * @result Unauthorized exception occurs.
     */
    @Test(expected = UnauthorizedException.class)
    public void testA() {
        System.out.println("testA");
        UUID secretId = secretServices.storeSecret("Alice", new Secret());
        secretServices.readSecret("Bob", secretId);
    }

    /**
     * TestB: Alice shares a secret with Bob, and Bob can read it.
     * @result Bob can read the secret.
     */
    @Test
    public void testB() {

        System.out.println("testB");

        //Alice shares a secret with Bob
        UUID secretId = secretServices.storeSecret("Alice", new Secret());
        secretServices.shareSecret("Alice", secretId, "Bob");

        //Bob can read it
        secretServices.readSecret("Bob", secretId);
    }

    /**
     * TestC: Alice shares a secret with Bob, and Bob shares Alice’s secret with Carl, and Carl can read this secret.
     * @result Carl can read Alice's secret share by Bob.
     */
    @Test
    public void testC() {

        System.out.println("testC");

        //Alice shares a secret with Bob
        UUID secretId = secretServices.storeSecret("Alice", new Secret());
        secretServices.shareSecret("Alice", secretId, "Bob");

        //Bob shares Alice’s secret with Carl
        secretServices.shareSecret("Bob", secretId, "Carl");

        //Carl can read this secret
        secretServices.readSecret("Carl", secretId);
    }

    /**
     * TestD: Alice shares her secret with Bob; Bob shares Carl’s secret with Alice and encounters UnauthorizedException.
     * @result Unauthorized exception occurs.
     */
    @Test(expected = UnauthorizedException.class)
    public void testD() {

        System.out.println("testD");

        //Alice shares her secret with Bob
        UUID aliceSecretId = secretServices.storeSecret("Alice", new Secret());
        secretServices.shareSecret("Alice", aliceSecretId, "Bob");

        //Bob shares Carl’s secret with Alice and encounters UnauthorizedException
        UUID carlSecretId = secretServices.storeSecret("Carl", new Secret());
        secretServices.shareSecret("Bob", carlSecretId, "Alice");
    }

    /**
     * TestE: Alice shares a secret with Bob, Bob shares it with Carl, Alice unshares it with Carl, and Carl cannot read this secret anymore.
     * @result Unauthorized exception occurs.
     */
    @Test(expected = UnauthorizedException.class)
    public void testE() {

        System.out.println("testE");

        //Alice shares a secret with Bob
        UUID aliceSecretId = secretServices.storeSecret("Alice", new Secret());
        secretServices.shareSecret("Alice", aliceSecretId, "Bob");

        //Bob shares it with Carl
        secretServices.shareSecret("Bob", aliceSecretId, "Carl");

        //Alice unshares it with Carl
        secretServices.unshareSecret("Alice", aliceSecretId, "Carl");

        //Carl cannot read this secret anymore
        secretServices.readSecret("Carl", aliceSecretId);
    }

    /**
     * TestF: Alice shares a secret with Bob and Carl; Carl shares it with Bob, then Alice unshares it with Bob; Bob cannot read this secret anymore.
     * @result Unauthorized exception occurs.
     */
    @Test(expected = UnauthorizedException.class)
    public void testF() {

        System.out.println("testF");

        //Alice shares a secret with Bob and Carl
        UUID aliceSecretId = secretServices.storeSecret("Alice", new Secret());
        secretServices.shareSecret("Alice", aliceSecretId, "Bob");
        secretServices.shareSecret("Alice", aliceSecretId, "Carl");

        //Carl shares it with Bob
        secretServices.shareSecret("Carl", aliceSecretId, "Bob");

        //Alice unshares it with Bob
        secretServices.unshareSecret("Alice", aliceSecretId, "Bob");

        //Bob cannot read this secret anymore
        secretServices.readSecret("Bob", aliceSecretId);
    }

    /**
     * TestG: Alice shares a secret with Bob; Bob shares it with Carl, and then unshares it with Carl. Carl can still read this secret.
     * @result Carl can still read this secret.
     */
    @Test
    public void testG() {

        System.out.println("testG");

        //Alice shares a secret with Bob
        UUID aliceSecretId = secretServices.storeSecret("Alice", new Secret());
        secretServices.shareSecret("Alice", aliceSecretId, "Bob");

        //Bob shares it with Carl
        secretServices.shareSecret("Bob", aliceSecretId, "Carl");

        //Bob unshares it with Carl
        secretServices.unshareSecret("Bob", aliceSecretId, "Carl");

        //Carl can still read this secret
        secretServices.readSecret("Carl", aliceSecretId);
    }

    /**
     * TestH: Alice shares a secret with Bob; Carl unshares it with Bob, and encounters UnauthorizedException.
     * @result Unauthorized exception occurs.
     */
    @Test(expected = UnauthorizedException.class)
    public void testH() {

        System.out.println("testH");

        //Alice shares a secret with Bob
        UUID aliceSecretId = secretServices.storeSecret("Alice", new Secret());
        secretServices.shareSecret("Alice", aliceSecretId, "Bob");

        //Carl unshares it with Bob, and encounters UnauthorizedException
        secretServices.unshareSecret("Carl", aliceSecretId, "Bob");
    }

    /**
     * TestI: Alice shares a secret with Bob; Bob shares it with Carl; Alice unshares it with Bob; Bob shares it with Carl with again, and encounters UnauthorizedException.
     * @result Unauthorized exception occurs.
     */
    @Test(expected = UnauthorizedException.class)
    public void testI() {

        System.out.println("testI");

        //Alice shares a secret with Bob
        UUID aliceSecretId = secretServices.storeSecret("Alice", new Secret());
        secretServices.shareSecret("Alice", aliceSecretId, "Bob");

        //Bob shares it with Carl
        secretServices.shareSecret("Bob", aliceSecretId, "Carl");

        //Alice unshares it with Bob
        secretServices.unshareSecret("Alice", aliceSecretId, "Bob");

        //Bob shares it with Carl again and encounters UnauthorizedException.
        secretServices.shareSecret("Bob", aliceSecretId, "Carl");
    }

    /**
     * TestJ: Alice stores the same secret object twice, and get two different UUIDs.
     * @result Alice gets two different UUIDs.
     */
    @Test
    public void testJ() {

        System.out.println("testJ");

        Secret secret = new Secret();

        //Alice stores the secret
        UUID aliceSecretId1 = secretServices.storeSecret("Alice", secret);

        //Alice stores again the same secret
        UUID aliceSecretId2 = secretServices.storeSecret("Alice", secret);

        //Alice get two different UUIDs
        assertNotEquals(aliceSecretId1, aliceSecretId2);
    }
}
