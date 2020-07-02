/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package easymockproject;

/**
 *
 * @author somasundaram
 */
public interface Collaborator {
    public void documentCreated(String title);
    public void documentRead(String title);
    public void documentUpdated(String title);
    public void documentDeleted(String title);
    public byte voteForDeletion(String title);
    public byte voteForDeletions(String... titles);
}
