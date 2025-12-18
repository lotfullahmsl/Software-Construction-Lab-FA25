public class Main
{
    public static void main(String[] args)
    {
        Simulator simulator = new Simulator();

        // Run simulation for fixed steps
        for (int step = 1; step <= 20; step++) {
            System.out.println("Simulation step: " + step);
            simulator.simulateOneStep();
        }

        System.out.println("Simulation finished.");
    }
}
