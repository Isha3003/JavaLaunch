package com.javalaunch.service.run;

import org.springframework.stereotype.Service;
import com.javalaunch.service.port.PortService;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RunService {
    private final PortService portService;
    private final Map<Integer, Process> runningProcesses =
        new ConcurrentHashMap<>();

private final Map<Integer, File> runningJars =
        new ConcurrentHashMap<>();
        private final Map<Integer, StringBuilder> processLogs =
        new ConcurrentHashMap<>();
    public RunService(PortService portService) {
        this.portService = portService;
    }

    public File findJar(File projectDirectory) {

        File targetFolder = new File(projectDirectory, "target");

        File[] files = targetFolder.listFiles();

        if (files == null) {
            throw new RuntimeException("Target folder not found.");
        }

        for (File file : files) {

            if (file.getName().endsWith(".jar")
                    && !file.getName().endsWith(".jar.original")) {

                return file;
            }
        }

        throw new RuntimeException("Jar file not found.");
    }

    public int runJar(File jarFile) {

    try {

        int port = portService.findAvailablePort();

        ProcessBuilder processBuilder = new ProcessBuilder(
        "java",
        "-jar",
        jarFile.getAbsolutePath(),
        "--server.port=" + port
);

        

       Process process = processBuilder.start();

processLogs.put(port, new StringBuilder());

captureLogs(port, process);

runningProcesses.put(port, process);
runningJars.put(port, jarFile);

return port;

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
public long getProcessId(int port) {

    Process process = runningProcesses.get(port);

    if (process == null) {
        throw new RuntimeException(
                "No running process found on port " + port
        );
    }

    return process.pid();
}
public void stopProcessById(long processId) {

    ProcessHandle processHandle = ProcessHandle.of(processId)
            .orElseThrow(() ->
                    new RuntimeException(
                            "Process not found: " + processId
                    )
            );

    processHandle.destroy();

    if (processHandle.isAlive()) {
        processHandle.destroyForcibly();
    }
}
public boolean isProcessRunning(Long processId) {

    if (processId == null) {
        return false;
    }

    return ProcessHandle.of(processId)
            .map(ProcessHandle::isAlive)
            .orElse(false);
}
private void captureLogs(int port, Process process) {

    Thread outputThread = new Thread(() -> {

        try (var reader =
                     new java.io.BufferedReader(
                             new java.io.InputStreamReader(
                                     process.getInputStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                processLogs.get(port).append(line).append("\n");
            }

        } catch (IOException e) {
            processLogs.get(port)
                    .append("Error reading application logs: ")
                    .append(e.getMessage())
                    .append("\n");
        }

    });

    Thread errorThread = new Thread(() -> {

        try (var reader =
                     new java.io.BufferedReader(
                             new java.io.InputStreamReader(
                                     process.getErrorStream()))) {

            String line;

            while ((line = reader.readLine()) != null) {
                processLogs.get(port)
                        .append("[ERROR] ")
                        .append(line)
                        .append("\n");
            }

        } catch (IOException e) {
            processLogs.get(port)
                    .append("Error reading application error logs: ")
                    .append(e.getMessage())
                    .append("\n");
        }

    });

    outputThread.start();
    errorThread.start();
}
public void stopJar(int port) {

    Process process = runningProcesses.get(port);

    if (process == null) {
        throw new RuntimeException("No running process found on port " + port);
    }

    process.destroy();

    runningProcesses.remove(port);
}
public int restartJar(int port) {

    File jarFile = runningJars.get(port);

    if (jarFile == null) {
        throw new RuntimeException(
                "No JAR found for port " + port
        );
    }

    Process oldProcess = runningProcesses.get(port);

    if (oldProcess != null) {
        oldProcess.destroy();
        runningProcesses.remove(port);
    }

    return runJar(jarFile);
}
public boolean isRunning(int port) {

    Process process = runningProcesses.get(port);

    if (process == null) {
        return false;
    }

    if (process.isAlive()) {
        return true;
    }

    runningProcesses.remove(port);

    return false;
}
public String getLogs(int port) {

    StringBuilder logs = processLogs.get(port);

    if (logs == null) {
        throw new RuntimeException(
                "No logs found for port " + port
        );
    }

    return logs.toString();
}

}