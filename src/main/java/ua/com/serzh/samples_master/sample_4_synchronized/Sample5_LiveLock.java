package ua.com.serzh.samples_master.sample_4_synchronized;


import ua.com.serzh.samples_master.ThreadUtils;

// thanks http://stackoverflow.com/a/8863671
public class Sample5_LiveLock {

    static class Spoon {
        private Diner owner;

        public Spoon(Diner d) {
            owner = d;
        }

        public Diner getOwner() {
            return owner;
        }

        public synchronized void setOwner(Diner d) {
            owner = d;
        }

        public synchronized void use() {
            ThreadUtils.print(String.format("%s has eaten!", owner.name));
        }
    }

    static class Diner {
        private String name;
        private boolean isHungry;

        public Diner(String n) {
            name = n;
            isHungry = true;
        }

        public String getName() {
            return name;
        }

        public boolean isHungry() {
            return isHungry;
        }

        public void eatWith(Spoon spoon, Diner spouse) {
            while (isHungry) {
                // Don't have the spoon, so wait patiently for spouse.
                if (spoon.owner != this) {
                    ThreadUtils.sleep(1);
                    continue;
                }

                // If spouse is hungry, insist upon passing the spoon.
                if (spouse.isHungry()) {
                    ThreadUtils.print(String.format("%s: You eat first my darling %s!%n",
                            name, spouse.getName()));
                    spoon.setOwner(spouse);
                    continue;
                }

                // Spouse wasn't hungry, so finally eat
                spoon.use();
                isHungry = false;
                ThreadUtils.print(String.format("%s: I am stuffed, my darling %s!%n",
                        name, spouse.getName()));
                spoon.setOwner(spouse);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Diner husband = new Diner("Bob");
        final Diner wife = new Diner("Alice");

        final Spoon s = new Spoon(husband);

        new Thread(() ->
                husband.eatWith(s, wife)).start();

        new Thread(new Runnable() {
            public void run() {
                wife.eatWith(s, husband);
            }
        }).start();
    }
}
