package ProjectNotes;

import java.io.IOException;
import java.util.*;

// Create a Note class to represent individual notes
class Note {
    private String title;
    private String content;
    private boolean pinned;

    // Constructor to initialize a Note with title and content
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.pinned = false; // Default: Not pinned
    }
    
    // Getter for pinned status
    public boolean isPinned() {
    	return pinned;
    }
    
    // Setter for pinned status
    public void setPinned(boolean pinned) {
    	this.pinned = pinned;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for content
    public String getContent() {
        return content;
    }

    // Setter for content
    public void setContent(String content) {
        this.content = content;
    }
}

// Main class for the Note Management System
public class Note_V1 {
    static List<Note> notes = new ArrayList<>();
    static String passcode = "ritu";

//**************************************************************************************************************
    // Method to save each note to a separate file
    private static void saveNotesToFiles() {
        File notesFolder = new File("notes");
        if (!notesFolder.exists()) {
        	notesFolder.mkdir();
        }

        for (Note note : notes) {
            String filename = "notes/" + note.getTitle() + ".txt";
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(note);
                System.out.println("Note saved to file: " + filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to load notes from files and convert them into a list
    private static List<Note> loadNotesFromFiles() {
        List<Note> loadedNotes = new ArrayList<>();
        File notesFolder = new File("notes");
        File[] noteFiles = notesFolder.listFiles();

        if (noteFiles != null) {
            for (File noteFile : noteFiles) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(noteFile))) {
                    Note note = (Note) in.readObject();
                    loadedNotes.add(note);
                    System.out.println("Note loaded from file: " + noteFile.getName());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return loadedNotes;
    }
//**************************************************************************************************************

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Check if a passcode is set; if not, set a new one
        if (passcode == null) {
            System.out.println("No password set. Please set a new password: ");
            passcode = scanner.nextLine();
            System.out.println("Password set successfully");
        } else {
            System.out.println("Enter password");
            String passkey = scanner.nextLine();

            // Check if the entered password matches the set passcode
            if (!passkey.equals(passcode)) {
                System.out.println("Access denied! Incorrect password");
                System.exit(1);
            } else {
                System.out.println("Access granted");
            }

            notes = loadNotesFromFiles();

            // Main menu loop
            while (true) {
                System.out.println("Menu:");
                System.out.println("1. Add note");
                System.out.println("2. List notes");
                System.out.println("3. View note");
                System.out.println("4. Delete note");
                System.out.println("5. Pin or Unpin note");
                System.out.println("6. Search in notes");
                System.out.println("7. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addNote();
                        break;
                    case 2:
                        listNotes();
                        break;
                    case 3:
                        viewNote();
                        break;
                    case 4:
                        deleteNote();
                        break;
                    case 5:
                        pinUnpin();
                        break;
                    case 6:
                        searchNote();
                        break;
                    case 7:
                        saveNotesToFiles():
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }

    // Method to add a new note
    private static void addNote() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the title of the note: ");
        String title = scanner.nextLine();

        System.out.println("Enter the content of the note: ");
        StringBuilder sb = new StringBuilder();
        String content = scanner.nextLine();
        while (!content.isEmpty()) {
            sb.append(content).append("\n");
            content = scanner.nextLine();
        }
        content = sb.toString();

        System.out.println("Do you want to pin this note? (Yes/No) : ");
        String pinChoice = scanner.nextLine();
        boolean pinned = pinChoice.equalsIgnoreCase("yes");

        // Create a new Note object and add it to the notes list
        Note note = new Note(title, content);
        note.setPinned(pinned);
        notes.add(note);

        System.out.println("Note added successfully!");
    }

    // Method to list notes
    private static void listNotes() {
        if (notes.isEmpty()) {
            System.out.println("No notes found.");
        } else {
            System.out.println("List of Pinned Notes : ");
            for (Note note : notes) {
                if (note.isPinned()) {
                    System.out.println(note.getTitle());
                }
            }
            System.out.println("\nList of Unpinned Notes : ");
            for (Note note : notes) {
                if (!note.isPinned()) {
                    System.out.println(note.getTitle());
                }
            }
        }
    }

    // Method to view a note
    private static void viewNote() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the title of the note to view: ");
        String title = scanner.nextLine();

        Note note = null;
        for (Note note1 : notes) {
            if (note1.getTitle().equals(title)) {
                note = note1;
                break;
            }
        }

        if (note == null) {
            System.out.println("Note not found.");
        } else {
            System.out.println(note.getContent());
        }
    }

    // Method to pin or unpin a note
    private static void pinUnpin() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the title of the note to pin or unpin");
        String title = scanner.nextLine();

        Note note = null;
        for (Note note1 : notes) {
            if (note1.getTitle().equals(title)) {
                note = note1;
                break;
            }
        }

        if (note != null) {
            // Toggle the pinned status
            if (note.isPinned()) {
                note.setPinned(false);
            } else {
                note.setPinned(true);
            }
        }
    }

    // Method to delete a note
    private static void deleteNote() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the title of the note to delete: ");
        String title = scanner.nextLine();

        Note note = null;
        for (Note note1 : notes) {
            if (note1.getTitle().equals(title)) {
                note = note1;
                break;
            }
        }

        if (note == null) {
            System.out.println("Note not found.");
        } else {
            notes.remove(note);
            System.out.println("Note deleted successfully!");
        }
    }

    // Method to search for a note by keyword or phrase
    private static void searchNote() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a keyword or phase to search for in the notes : ");
        String phrase = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Note item : notes) {
            if (item.getTitle().toLowerCase().contains(phrase) || item.getContent().toLowerCase().contains(phrase)) {
                found = true;
                System.out.println("Note found : ");
                System.out.println("Title of the note : " + item.getTitle());
                System.out.println("Content of the note : " + item.getContent());
            }
        }

        if (!found) {
            System.out.println("No matching notes found.");
        }
    }
}
