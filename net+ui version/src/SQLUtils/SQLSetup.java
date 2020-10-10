package SQLUtils;

import DataType.Word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Formatter;

public class SQLSetup {

    private static String defaultPath = "src/data/dict.txt";

    public static void setup(String path, String audioPath, Connection conn) {
        if (path.equals("")) {
            path = defaultPath;
        }

        try {
            BufferedReader fr = new BufferedReader(new FileReader(path));
            LineNumberReader reader = new LineNumberReader(new FileReader(path));
            while((reader.readLine()) != null);

            int lines = reader.getLineNumber();
            int count = 0;

            String line = "";

            String word = "";
            String pronun = "";
            String definition = "";
            String audioLink = "";

            while ((line = fr.readLine()) != null) {
                ++count;
                if (count % 100 == 0) {
                    System.out.println("Complete " + count + " over " + lines);
                }
                if (line.length() == 0 || line.equals("???")) {
                    addDataToSQL(conn, word, definition, pronun, audioLink);
                    word = "";
                } else if (line.charAt(0) == '@') {
                    int lastPost = line.length() - 1;

                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == '/') {
                            lastPost = i;
                            break;
                        }
                    }

                    word = line.substring(0, lastPost - 1);
                    if (lastPost + 1 <= line.length() - 2) {
                        pronun = line.substring(lastPost + 1, line.length() - 2);
                    } else {
                        pronun = "";
                    }
                    definition = "";
                    audioLink = "";
                } else {
                    definition = definition + "\n" + line;
                }
            }

            if (!word.equals("")) {
                addDataToSQL(conn, word, definition, pronun, audioLink);
            }
        } catch (Exception e) {
            System.out.println("Setup/SQLSetup Exception: " + e);
        }
    }

    public static Word getDataFromSQL(Connection conn, String path) {
        try {
            return null;
        } catch (Exception e) {
            System.out.println("Get Query Exception: " + e);
            return null;
        }
    }

    public static void addDataToSQL(Connection conn, String word, String definition, String pronun, String audioLink) {
        try {
            if (!pronun.equals("")) {
                for (int i = 0; i < pronun.length(); i++) {
                    if (pronun.charAt(i) == 39) {
                        pronun = pronun.substring(0, i) + "'" + pronun.substring(i);
                        i = i + 1;
                    }
                }
            }
            if (!definition.equals("")) {
                for (int i = 0;i < definition.length(); i++) {
                    if (definition.charAt(i) == 39) {
                        definition = definition.substring(0, i) + "'" + definition.substring(i);
                        i = i + 1;
                    }
                }
            }
            Statement statement = conn.createStatement();

            StringBuilder sql = new StringBuilder();
            Formatter sqlFmt = new Formatter(sql);
            word = word.substring(1);
            sqlFmt.format("insert into Dictionary.dbo.word values ('%s', N'%s', N'%s', '%s');",
                    word, definition, pronun, audioLink);
            statement.execute(sqlFmt.toString());
        } catch (Exception e) {
            System.out.println("Add Query Exception: " + e);
            System.out.println(word);
            System.out.println(definition);
            System.out.println(pronun);
            System.out.println();
        }
    }

    public static void updateDataToSQL(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            String sql = "update Dictionary.dbo.word set WordExpland=N'Chào' where WordTarget='Dante';";
            statement.execute(sql);
        } catch (Exception e) {
            System.out.println("Update Query Exception: " + e);
        }
    }

    public static void truncateTabelSQL(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            String sql = "truncate table Dictionary.dbo.word";
            statement.execute(sql);
        } catch (Exception e) {
            System.out.println("Truncate Query Exception");
        }
    }

    public static void main(String[] args) {
        try {
            Connection conn = SqlJdbcUtils.getSQLServerConnection();
            //truncateTabelSQL(conn);
            setup("", "", conn);
        } catch (Exception e) {
            System.out.println("Somehow you fail :< " + e);
        }
    }
}
