package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    protected String regex;
    protected String rootPath;
    protected String outFile;

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        // User default logger config
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch(Exception e) {
            javaGrepImp.logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        List<File> files = this.listFiles(this.rootPath);

        for(File file: files) {
            List<String> lines = this.readLines(file);

            for(String line: lines) {
                if(this.containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }

        this.writeToFile(matchedLines);

    }

    @Override
    public List<File> listFiles(String rootDir) {
        File currentDir = new File(rootDir);
        File[] fileArray = currentDir.listFiles();

        if(fileArray == null) {
            fileArray = new File[]{};
        }

        List<File> fileList = new ArrayList<>(Arrays.asList(fileArray));
        ListIterator<File> fileListIterator = fileList.listIterator();
        List<File> childrenFiles = new ArrayList<>();

        while(fileListIterator.hasNext()) {
            File currentFile = fileListIterator.next();

            if(currentFile.isDirectory()) {
                fileListIterator.remove();
                childrenFiles.addAll(this.listFiles(currentFile.getAbsolutePath()));
            }
        }

        fileList.addAll(childrenFiles);

        return fileList;
    }

    @Override
    public List<String> readLines(File inputFile) throws IllegalArgumentException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            List<String> lines = new ArrayList<>();
            String line;

            while((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();

            return lines;

        } catch(FileNotFoundException e) {
            throw new IllegalArgumentException("Given file does not exist");
        } catch (IOException e) {
            throw new IllegalArgumentException("Given file cannot be read");
        }

    }

    @Override
    public boolean containsPattern(String line) {
        Pattern pattern = Pattern.compile(this.regex);
        Matcher matcher = pattern.matcher(line);
        return matcher.find();
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        File file = new File(this.outFile);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

        for(String line: lines) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }
}
