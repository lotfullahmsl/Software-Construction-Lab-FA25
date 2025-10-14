import java.util.*;

public class Task2 {
    private HashMap<String, List<String>> helpData = new HashMap<>();
    private List<String> chatHistory = new ArrayList<>();
    private Scanner in = new Scanner(System.in);
    private Random random = new Random();

    public Task2() {
        setupHelpData();
    }

    private void setupHelpData() {
        helpData.put("slow", Arrays.asList(
                "Try restarting your system and closing unused programs.",
                "Clear temporary files. They sometimes slow down the computer.",
                "Make sure your disk isn’t full. That affects performance."
        ));

        helpData.put("internet", Arrays.asList(
                "Check if your Wi-Fi is connected properly.",
                "Try restarting the router.",
                "Test another device to confirm if the issue is with your connection."
        ));

        helpData.put("battery", Arrays.asList(
                "Lower the screen brightness to save power.",
                "Close background apps you aren’t using.",
                "Consider replacing the battery if it drains too fast."
        ));

        helpData.put("error", Arrays.asList(
                "Can you share the error message you’re seeing?",
                "Try restarting the app showing the error.",
                "It might help to reinstall the program."
        ));

        helpData.put("update", Arrays.asList(
                "Check if system updates are pending.",
                "Updates can fix bugs and performance issues.",
                "Avoid turning off your system during updates."
        ));
    }

    private String findReply(String userLine) {
        userLine = userLine.toLowerCase();
        for (String word : helpData.keySet()) {
            if (userLine.contains(word)) {
                List<String> replies = helpData.get(word);
                return replies.get(random.nextInt(replies.size()));
            }
        }
        return "I’m not sure about that. Could you explain your problem a bit more?";
    }

    private void startChat() {
        System.out.println("Tech Support Assistant");
        System.out.println("Type 'bye' when you’re done.\n");

        while (true) {
            System.out.print("You: ");
            String userLine = in.nextLine().trim();
            chatHistory.add("You: " + userLine);

            if (userLine.equalsIgnoreCase("bye")) {
                System.out.println("Support: Glad I could help. Goodbye.");
                break;
            }

            String reply = findReply(userLine);
            System.out.println("Support: " + reply);
            chatHistory.add("Support: " + reply);
        }

        showHistory();
    }

    private void showHistory() {
        System.out.println("\nConversation Summary:");
        for (String line : chatHistory) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) {
        Task2 bot = new Task2();
        bot.startChat();
    }
}
