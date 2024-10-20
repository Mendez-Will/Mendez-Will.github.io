#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Feb  3 13:31:56 2024

@author: wilfredomende_snhu
"""

from pymongo import MongoClient


class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self):
        # Initializing the MongoClient. This helps to 
        # access the MongoDB databases and collections.
        # This is hard-wired to use the aac database, the 
        # animals collection, and the aac user.
        # Definitions of the connection string variables are
        # unique to the individual Apporto environment.
        #
        # You must edit the connection variables below to reflect
        # your own instance of MongoDB!
        #
        # Connection Variables
        #
        USER = 'aacuser'
        PASS = 'Turtle1'
        HOST = 'nv-desktop-services.apporto.com'
        PORT = 31307
        DB = 'AAC'
        COL = 'animals'
        #
        # Initialize Connection
        #
        self.client = MongoClient('mongodb://%s:%s@%s:%d' % (USER,PASS,HOST,PORT))
        self.database = self.client['%s' % (DB)]
        self.collection = self.database['%s' % (COL)]

# Complete this create method to implement the C in CRUD.
    def create(self, data):
        if data is not None:
            self.collection.insert_one(data)  # data should be dictionary            
        else:
            raise Exception("Nothing to save, because data parameter is empty")

# Create method to implement the R in CRUD.
    def read(self, query):
        cursor = self.collection.find(query)
        return list(cursor)
    
    def update(self, query, new_data):
        if query is not None and new_data is not None:
            result = self.collection.update_many(query, {"$set": new_data})
            return result.modified_count  # Return the count of modified documents
        else:
            raise Exception("Query or new data cannot be empty")

    def delete(self, query):
        if query is not None:
            result = self.collection.delete_many(query)
            return result.deleted_count  # Return the count of deleted documents
        else:
            raise Exception("Query cannot be empty")