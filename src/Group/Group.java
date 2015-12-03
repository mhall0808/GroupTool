/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Group;

import DiscussionBoard.DiscussionBoard;
import java.util.ArrayList;

/**
 *
 * @author hallm8
 */
public class Group {

    private String groupName;
    private ArrayList<DiscussionBoard> discussionBoards;
    private String numGroups;

    /**
     *
     */
    public Group() {
        groupName = "default";
        discussionBoards = new ArrayList<>();
        
    }

    public Group(String groupName, ArrayList<DiscussionBoard> discussionBoards, String numGroups) {
        this.groupName = groupName;
        this.discussionBoards = discussionBoards;
        this.numGroups = numGroups;
    }
    
    

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<DiscussionBoard> getDiscussionBoards() {
        return discussionBoards;
    }

    public void setDiscussionBoards(ArrayList<DiscussionBoard> discussionBoards) {
        this.discussionBoards = discussionBoards;
    }

    public String getNumGroups() {
        return numGroups;
    }

    public void setNumGroups(String numGroups) {
        this.numGroups = numGroups;
    }
    
    public String getGText(){
        
        return "Group Name: " + groupName + " <" + discussionBoards.size() + " boards>";
    }
    
}