package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;
import static gitlet.Utils.readObject;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {

    /** The message of this Commit. */
    private final String message;

    /** The date and time this Commit was created. */
    private final Date timestamp;

    /** The parent of this Commit, null if this is the initial Commit. */
    private final String parent;

    /** An array of blob IDs that this commit points to. */
    private final List<String> blobs;

    /** The 40 character sha1 hash of this Commit's data. */
    private final String ID;

    /** Creates a new commit with no parent and the init message. */
    public Commit() {
        parent = "none";
        timestamp = new Date(0);
        message = "initial commit";
        blobs = new ArrayList<>();
        ID = generateID();
    }

    /** Creates a new commit with the specified parent and message. */
    public Commit(String parent, String message, List<String> blobs) {
        this.parent = parent;
        timestamp = new Date();
        this.message = message;
        this.blobs = blobs;
        ID = generateID();
    }

    /** Generates this Commit's ID by hashing its attribute values. */
    private String generateID() {
        List<Object> hashBrowns = new ArrayList<>();
        hashBrowns.add(parent);
        hashBrowns.add(timestamp.toString());
        hashBrowns.add(message);
        hashBrowns.addAll(blobs);
        return sha1(hashBrowns);
    }

    /** Returns this Commit's ID. */
    public String getID() {
        return ID;
    }

    public List<String> getBlobs() {
        return blobs;
    }

    public void saveCommit() {
        File new_commit = join(Repository.COMMITS_DIR, this.ID);
        writeObject(new_commit, this);
    }

    public String toString() {
        return "commit " + this.ID + "\n" +
                "Date: " + this.timestamp + "\n" +
                this.message;
    }


}
