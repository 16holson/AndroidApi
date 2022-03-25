package edu.weber.w01311060.cs3270a9.models;



public class Assignments
{
    public Assignments(String name)
    {
        this.name = name;
    }

    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
