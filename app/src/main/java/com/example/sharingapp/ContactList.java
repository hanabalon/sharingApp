package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactList {
    private ArrayList<Contact> contacts;
    private String FILENAME = "contacts.sav";

    public ContactList () { contacts = new ArrayList<Contact>(); }
    public void setContacts(ArrayList<Contact> contact_list){ contacts = contact_list; }
    public ArrayList<Contact> getContacts () { return contacts; }

    public ArrayList<String> getAllUsernames () {
        ArrayList<String> usernames_list = new ArrayList<>();
        for (Contact i: contacts) {
            usernames_list.add(i.getUsername());
        }
        return usernames_list;
    }

    public void addContact (Contact contact) { contacts.add(contact); }
    public void deleteContact (Contact contact) { contacts.remove(contact); }
    public Contact getContact (Integer index) { return contacts.get(index); }
    public Integer getSize() { return contacts.size(); }
    public Integer getIndex(Contact contact) {
        Integer index = 0;

        for (Contact i: contacts) {
            if (i.getId().equals(contact.getId())) {
                return index;
            }
            index++;
        }
        return null;
    }

    public boolean hasContact (Contact contact) {
        for (Contact i: contacts) {
            if (i.getId().equals(contact.getId())) {
                return true;
            }
        }
        return false;
    }

    public Contact getContactByUsername(String username) {
        for (Contact i: contacts) {
            if (i.getUsername().equals(username)) {
                return i;
            }
        }
        return null;
    }

    public void loadContacts (Context context) {
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = gson.fromJson(isr, listType); // temporary
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<Contact>();
        } catch (IOException e) {
            contacts = new ArrayList<Contact>();
        }
    }

    public void saveContacts (Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameAvailable (String username) {
        Boolean is_exists = false;

        for (Contact i: contacts) {
            if (i.getUsername().equals(username)) {
                is_exists = true;
            }
        }
        return !is_exists;
    }
}
