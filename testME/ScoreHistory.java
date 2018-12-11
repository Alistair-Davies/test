package ass2.ass2;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ScoreHistory {
    Path file;
    String fileName;

    public ScoreHistory(String filename) {
        this.fileName = filename;
        this.file = Paths.get(filename);
        File f = new File(filename);
        if (!f.exists()) {
            List<String> l = new ArrayList(Arrays.asList());
            try {
                Files.write(file, l, Charset.forName("UTF-8"));

            }
            catch(IOException exc) {
                System.out.println(exc.toString());
            }
        }

    }

    public boolean write(int score) {
        try {
            FileWriter fileWriter = new FileWriter(this.fileName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(score);
            printWriter.close();
            return true;
        }
        catch (IOException exc) {
            System.out.println(exc.toString());
            return false;
        }
    }

    public String getTop10() {
            String result="\n";
            ArrayList scoresRead = readAllScores();

            Iterator itr;
            if (scoresRead.size() < 10) {
                ArrayList scores = new ArrayList<Integer>(scoresRead.subList(0, scoresRead.size()));
                itr = scores.iterator();
            }
            else {
                ArrayList scores= new ArrayList<Integer>(scoresRead.subList(0, 10));
                itr = scores.iterator();
            }
            int count = 1;
            while (itr.hasNext()) {
                Integer points =  (Integer) itr.next();
                result = result +count+". "+ points+" \n";
                count++;
            }
            if (count < 10) {
                for (int i=count; i<11; i++) {
                    result = result + i+". "+"\n";
                }
            }
            return result;
    }

    public ArrayList readAllScores() {
        ArrayList<Integer> scoreList = new ArrayList<Integer>();
        try {
            String line;
            BufferedReader b = new BufferedReader(new FileReader(fileName));
            int score;

            while ((line = b.readLine()) != null) {
                score = (Integer.parseInt(line));
                scoreList.add(score);
            }
            Collections.sort(scoreList, Collections.<Integer>reverseOrder());

        }
        catch (IOException exc) {
            System.out.println(exc.toString());
        }
        return scoreList;
    }

    public String getRank(int score) {
        ArrayList scoresRead = readAllScores();
        int total = scoresRead.size();
        int position = scoresRead.indexOf(score)+1;
        return position + " / " + total;
    }

    public void clear() {
        try {
            Files.write(file, new ArrayList(Arrays.asList()), Charset.forName("UTF-8"));

        }
        catch(IOException exc) {
            System.out.println(exc.toString());
        }

    }
}
