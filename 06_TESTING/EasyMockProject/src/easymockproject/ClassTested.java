/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package easymockproject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Maintains a Map of documents (title, byte[]).
 * Maintains a list of listeners/Collaborators to be notified
 * Have CRUD for Documents. Notify the listeners.
 * @author somasundaram
 */
public class ClassTested {

    private Map<String, byte[]> documentMap = new HashMap<String, byte[]>(10);

    private Set<Collaborator> collaborators = new HashSet<Collaborator>(10);

    public void addCollaborator(Collaborator listener) {
        collaborators.add(listener);
    }
    public void addDocument(String title, byte[] documents) {
        boolean documentChanged = documentMap.containsKey(title);
        documentMap.put(title, documents);
        if (documentChanged) {
            notifyChanged(title);
        }else {
            notifyAdded(title);
        }
    }
    public boolean removeDocument(String title) {
        if (!documentMap.containsKey(title)) {
            return false;
        }
        documentMap.remove(title);
        notifyRemoved(title);
        return true;
    }
    public boolean removeDocuments(String... titles) {
        for (String title : titles) {
            documentMap.remove(title);
            notifyRemoved(title);
        }
        return true;
    }
    private void notifyAdded(String title) {
        for (Collaborator c : collaborators) {
            c.documentCreated(title);
        }
    }
    private void notifyChanged(String title) {
        for (Collaborator c : collaborators) {
            c.documentUpdated(title);
        }
    }
    private void notifyRemoved(String title) {
        for (Collaborator c : collaborators) {
            c.documentDeleted(title);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
}
