/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package easymockproject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.easymock.EasyMock;

/**
 *
 * @author somasundaram
 */
public class ClassTestedTest {

    private Collaborator listener = null;

    public ClassTestedTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddCollaborator() {
        System.out.println("addCollaborator");
        ClassTested instance = new ClassTested();
        listener = EasyMock.createMock(Collaborator.class);
        try {
            instance.addCollaborator(listener);
            listener.documentCreated("Hi");
            //EasyMock.replay(listener);
            //listener.documentCreated("Hello");
        } catch (Exception e) {
            Assert.fail("Error in adding Collaborator");
        }
        Assert.assertTrue(true);
    }
/**
    @Test
    public void testAddDocument() {
        System.out.println("addDocument");
        String title = "";
        byte[] documents = null;
        ClassTested instance = new ClassTested();
        listener = EasyMock.createMock(Collaborator.class);
        instance.addCollaborator(listener);
        instance.addDocument(title, documents);
        EasyMock.replay(listener);
        EasyMock.verify(listener);
        //instance.addDocument(title, documents);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }**/
    @Test
    public void testAddDocumentMoreTimes() {
        System.out.println("addDocument");
        listener = EasyMock.createMock(Collaborator.class);
        listener.documentCreated("Hi");
        EasyMock.replay(listener);
        listener.documentCreated("Hi");
        //EasyMock.verify(listener);
    }
    /**
    @Test
    public void testRemoveDocument() {
        System.out.println("removeDocument");
        String title = "";
        ClassTested instance = new ClassTested();
        boolean expResult = false;
        boolean result = instance.removeDocument(title);
        Assert.assertEquals(expResult, result);
        /// TODO review the generated test code and remove the default call to fail.
        //("The test case is a prototype.");
    }

    @Test
    public void testRemoveDocuments() {
        System.out.println("removeDocuments");
        String[] titles = null;
        ClassTested instance = new ClassTested();
        boolean expResult = false;
        boolean result = instance.removeDocuments(titles);
         Assert.assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
**/
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        ClassTested.main(args);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
}
