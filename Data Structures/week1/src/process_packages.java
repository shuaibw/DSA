import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    public Buffer(int size) {
        this.size = size;
        this.buffer = new LinkedList<>();
    }

    public Response Process(Request request) {
        if (buffer.size() < size) {
            if (buffer.isEmpty()) {
                buffer.addLast(request.arrival_time + request.process_time);
                return new Response(false, request.arrival_time);
            }
            if (request.arrival_time < buffer.peekFirst()) {
                int start = buffer.peekLast();
                buffer.addLast(start + request.process_time);
                return new Response(false, start);
            }
        } else if (buffer.peekFirst() <= request.arrival_time) {
            int start = buffer.removeFirst();
            if (buffer.size() != 0) {
                start = buffer.peekLast();
            }
            if (start < request.arrival_time) start = request.arrival_time;
            buffer.addLast(start + request.process_time);
            return new Response(false, start);
        }
        return new Response(false, -1);
    }

    private int size;
    private LinkedList<Integer> buffer;
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
