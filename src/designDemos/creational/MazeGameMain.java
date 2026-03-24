// Example adapted from Gang of Four Design Patterns book

import java.util.HashSet;
import java.util.Set;

enum Direction {
    NORTH, SOUTH, EAST, WEST
}

abstract class MapSite {
    public void enter() {
        System.out.println("You just entered a MapSite. This should never happen.");
    }
}

class Room extends MapSite {
    private static int _roomCnt = 0;
    private int _roomNr;
    private MapSite _northSide;
    private MapSite _southSide;
    private MapSite _eastSide;
    private MapSite _westSide;

    Room() {
        _roomNr = _roomCnt++;
        System.out.println("creating Room #" + _roomNr);
    }
    void setSide(Direction d, MapSite site) {
        switch(d){
            case NORTH:
                _northSide = site;
                break;
            case SOUTH:
                _southSide = site; 
                break;
            case EAST:
                _eastSide = site;
                break;
            case WEST:
                _westSide = site;
                break;
        }
        System.out.println("setting " + d.toString() + " side of " + this.toString() + " to " + site.toString());
    }
    MapSite getSide(Direction d) {
        switch(d){
            case NORTH:
            return _northSide;
            case SOUTH:
            return _southSide;
            case EAST:
            return _eastSide;
            case WEST:
            return _westSide;
        }
        return null;
    }
    public String toString() {
        return "Room #" + _roomNr;
    }
}

class Wall extends MapSite {
    private static int _wallCnt = 0;
    private int _wallNr;
    Wall() {
        _wallNr = _wallCnt++;
        System.out.println("creating a Wall #" + _wallNr);
    }
    public void enter() {
        System.out.println("You just walked into a wall. Ouch!");
    }
    public String toString() {
        return "Wall #" + _wallNr;
    }
}

class Door extends MapSite {
    private static int _doorCnt = 0;
    private int _doorNr;
    private Room _room1;
    private Room _room2;
    private boolean _isOpen;

    Door(Room r1, Room r2) {
        _doorNr = _doorCnt++;
        _room1 = r1;
        _room2 = r2;
        System.out.println("creating a Door #" + _doorNr + " between " + r1.toString() + " and " + r2.toString());
    }
    public void enter() {
        if (_isOpen) {
            System.out.println("You just walked through the door. Welcome to the next room!");
        }
        else {
            System.out.println("The door is closed. You can't go through it.");
        }
    }
    public String toString() {
        return "Door #" + _doorNr;
    }
}

class Maze {
    private Set<Room> _rooms = new HashSet<Room>();

    Maze() {
        System.out.println("Creating a new Maze object");
    }
    
    void addRoom(Room r) {
        if (!_rooms.contains(r)) {
            _rooms.add(r);
        }   
    }
}

class MazeGame {
    public Maze createMaze() {
        Maze maze = new Maze();
        Room r1 = new Room();  // 
        Room r2 = new Room();
        Door d1 = new Door(r1, r2);
        r1.setSide(Direction.NORTH, d1);
        r1.setSide(Direction.SOUTH, new Wall());
        r1.setSide(Direction.EAST, new Wall());
        r1.setSide(Direction.WEST, new Wall());
        r2.setSide(Direction.NORTH, new Wall());
        r2.setSide(Direction.SOUTH, new Wall());
        r2.setSide(Direction.EAST, new Wall());
        r2.setSide(Direction.WEST, d1);
        return maze;
    }
}
/* 
class MazeGame {

    // MazeFactory factory = new MazeFactory();
    MazeFactory factory = MazeFactory.getInstance();  // Singleton

    protected Room makeRoom() {
        // return new Room();
        return factory.makeRoom();
    }

    protected Door makeDoor(Room r1, Room r2) {
        // return new Door(r1, r2);
        return factory.makeDoor(r1, r2);
    }

    protected Wall makeWall() {
        // return new Wall();
        return factory.makeWall();
    }

    public Maze createMaze() {

        Maze maze = new Maze();

        Room r1 = makeRoom();
        Room r2 = makeRoom();

        Door d1 = makeDoor(r1,r2);

        r1.setSide(Direction.NORTH, d1);
        r1.setSide(Direction.SOUTH, makeWall());
        r1.setSide(Direction.EAST, makeWall());
        r1.setSide(Direction.WEST, makeWall());

        return maze;
    }
}

class MazeFactory {

    // Singleton

    private static MazeFactory instance = new MazeFactory();

    private MazeFactory() {
        System.out.println("MazeFactory singleton created");
    }

    public static MazeFactory getInstance() {
        return instance;
    }


    // end singleton

    public Maze makeMaze() {
        return new Maze();
    }

    public Wall makeWall() {
        return new Wall();
    }

    public Room makeRoom() {
        return new Room();
    }

    public Door makeDoor(Room r1, Room r2) {
        return new Door(r1, r2);
    }
}

class BossMazeGame extends MazeGame {
    @Override
    protected Room makeRoom() {
        return new Room(); // could be a BossRoom subclass of Room
    }

    @Override
    protected Door makeDoor(Room r1, Room r2) {
        return new Door(r1, r2); // could be a BossDoor subclass of Door
    }
}
*/

public class MazeGameMain {
    public static void main(String[] args) {
        MazeGame game = new MazeGame();
        Maze maze = game.createMaze();
    }
}
