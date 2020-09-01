package com.techelevator.authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import com.techelevator.user.User;
import com.techelevator.user.UserDao;

import org.junit.Before;
import org.junit.Test;

/**
 * RequestAuthProviderTest
 */
public class RequestAuthProviderTest {
    private RequestAuthProvider sut;
    private HttpServletRequest mockedRequest;
    private UserDao mockedDao;

    @Before
    public void before() {
        mockedRequest = mock(HttpServletRequest.class);
        mockedDao = mock(UserDao.class);
        sut = new RequestAuthProvider(mockedRequest, mockedDao);
    }

    @Test
    public void testLogOut() {
        sut.logOff();

        verify(mockedRequest).removeAttribute(RequestAuthProvider.USER_KEY);
    }

    @Test
    public void isLoggedInSuccessTest() {
        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(new User());

        assertTrue(sut.isLoggedIn());
    }

    @Test
    public void isLoggedInFailTest() {
        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(null);

        assertFalse(sut.isLoggedIn());
    }

    @Test
    public void getCurrentUserWithUserTest() {
        User mockedUser = new User();
        mockedUser.setId(0);
        mockedUser.setEmail("TEST");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(mockedUser);

        User fromSut = sut.getCurrentUser();

        assertEquals(mockedUser.getId(), fromSut.getId());
        assertEquals(mockedUser.getEmail(), fromSut.getEmail());
    }

    @Test
    public void getCurrentUserWithNullTest() {
        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(null);

        User fromSut = sut.getCurrentUser();

        assertNull(fromSut);
    }

    @Test
    public void registerTest() {
        sut.register("TEST", "TESTPASS", "TESTROLE");

        verify(mockedDao).saveUser("TEST", "TESTPASS", "TESTROLE");
    }

    @Test
    public void signInSuccessTest() {
        User testUser = new User();
        testUser.setId(0);
        testUser.setEmail("TEST");
        when(mockedDao.getValidUserWithPassword("TEST", "TEST")).thenReturn(testUser);

        assertTrue(sut.signIn("TEST", "TEST"));
        verify(mockedRequest).setAttribute(RequestAuthProvider.USER_KEY, testUser);
    }

    @Test
    public void signInFailTest() {
        when(mockedDao.getValidUserWithPassword("TEST", "TEST")).thenReturn(null);

        assertFalse(sut.signIn("TEST", "TEST"));
    }

//    @Test
//    public void changePasswordSuccessTest() {
//        User testUser = new User();
//        testUser.setId(0);
//        testUser.setEmail("TEST");
//
//        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);
//        when(mockedDao.getValidUserWithPassword("TEST", "TEST")).thenReturn(testUser);
//
//        assertTrue(sut.changePassword("TEST", "NEWVALUE"));
//        verify(mockedDao).changePassword(testUser, "NEWVALUE");
//    }

//    @Test
//    public void changePasswordBadPasswordTest() {
//        User testUser = new User();
//        testUser.setId(0);
//        testUser.setEmail("TEST");
//
//        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);
//        when(mockedDao.getValidUserWithPassword("TEST", "TEST")).thenReturn(null);
//
//        assertFalse(sut.changePassword("TEST", "NEWVALUE"));
//        verify(mockedDao, times(0)).changePassword(testUser, "NEWVALUE");
//    }

//    @Test
//    public void changePasswordNoOneLoggedInTest() {
//        User testUser = new User();
//        testUser.setId(0);
//        testUser.setEmail("TEST");
//
//        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(null);
//        when(mockedDao.getValidUserWithPassword("TEST", "TEST")).thenReturn(testUser);
//
//        assertFalse(sut.changePassword("TEST", "NEWVALUE"));
//        verify(mockedDao, times(0)).changePassword(testUser, "NEWVALUE");
//    }

    @Test
    public void hasRoleSuccessTest() {
        User testUser = new User();
        testUser.setId(0);
        testUser.setEmail("TEST");
        testUser.setPermission("user");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertTrue(sut.userHasPermission(new String[] { "user" }));
    }

    @Test
    public void hasRoleFailTest() {
        User testUser = new User();
        testUser.setId(0);
        testUser.setEmail("TEST");
        testUser.setPermission("user");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertFalse(sut.userHasPermission(new String[] { "admin" }));
    }

    @Test
    public void hasRoleMultipleSuccessTest() {
        User testUser = new User();
        testUser.setId(0);
        testUser.setEmail("TEST");
        testUser.setPermission("user");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertTrue(sut.userHasPermission(new String[] { "admin", "user", "editor" }));
    }

    @Test
    public void hasRoleMultipleFailTest() {
        User testUser = new User();
        testUser.setId(0);
        testUser.setEmail("TEST");
        testUser.setPermission("user");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertFalse(sut.userHasPermission(new String[] { "admin", "manager", "editor" }));
    }

    @Test
    public void hasRoleNullTest() {
        User testUser = new User();
        testUser.setId(0);
        testUser.setEmail("TEST");
        testUser.setPermission("user");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertFalse(sut.userHasPermission(null));
    }

    @Test
    public void hasRoleEmptyTest() {
        User testUser = new User();
        testUser.setId(0);
        testUser.setEmail("TEST");
        testUser.setPermission("user");

        when(mockedRequest.getAttribute(RequestAuthProvider.USER_KEY)).thenReturn(testUser);

        assertFalse(sut.userHasPermission(new String[] {}));
    }
}