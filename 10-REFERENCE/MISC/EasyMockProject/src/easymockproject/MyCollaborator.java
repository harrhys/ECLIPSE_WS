/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package easymockproject;

/**
 *
 * @author somasundaram
 */
public class MyCollaborator implements Collaborator {

    public void documentCreated(String title) {
        System.out.println("Not supported yet.");
    }

    public void documentRead(String title) {
        System.out.println("Not supported yet.");
    }

    public void documentUpdated(String title) {
        System.out.println("Not supported yet.");
    }

    public void documentDeleted(String title) {
        System.out.println("Not supported yet.");
    }

    public byte voteForDeletion(String title) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public byte voteForDeletions(String... titles) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
