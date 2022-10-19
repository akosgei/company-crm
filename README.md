# CRM project

Conversations are the bread and butter of Help Scout's systems. Our domain space revolves around companies helping their
customers via email, chat and proactive knowledge base support. 

In this project you'll be working on an incomplete API that imports multiple companies and their relevant data. Your task
will be to enhance the current importing endpoint, apply filtering rules and add an endpoint to expose the newly-imported data.

 

## What you're starting with

This repo is a bare-bones Spring boot application with a single `POST` endpoint of `/company/import` and an H2 database.
The project has minimal tests that will import the provided data you'll be working with. The project also uses a mixture 
of Java and Kotlin. You may use either language.

You can view the in-memory database at http://localhost:8080/h2-console/ when running the application. Use the following details
to connect:
- Driver class: `org.h2.Driver`
- JDBC URL: `jdbc:h2:mem:crmdb`
- User Name: `crm`
- Password: [blank]

The supplied JSON import file contains a hierarchy of an array of companies. Each company may have an array of conversations, 
and each conversation may have an array of threads (replies within a conversation). Currently, the import endpoint will
only save the companies to the database and ignore the sub-entities.

## What you have to do

1. **Setup**: Create a branch in this repo where you carry out all your work. Feel free to add libraries, dependencies, frameworks or 
anything else you'd like to incorporate into the project.
2. **Persistence**: Enhance the import endpoint to persist the sub-entities.
3. **Refactoring**: Showcase your ability to refactor existing code.
4. **New business rules**: Implement a few filtering rules prior to importing the data.
5. **New API**: Add a new endpoint to display information about the saved data.
6. **Postmortem**: Write a postmortem of how the project went and anything you learned on the way.

Tasks 2-6 are explained in more detail below.

## Task 2: Persistence

You can inspect the `companies.json` example in the resources folder; a single company example from the array is listed below. Currently, 
the import endpoint will only save the companies to the database, but we need the conversations and threads (including all fields), to also
be persisted to their own tables.

```json
{
  "id": 7861,
  "name": "Dr. Enola Ortiz",
  "signedUp": "2020-09-05T17:21:21",
  "conversations": [
    {
      "id": 1504,
      "number": 5918,
      "userId": 3372,
      "from": "bret.nitzsche@example.com",
      "received": "2021-10-01T23:46:52",
      "threads": [
        {
          "id": 8591,
          "payload": "And then of course I've got this terrible pain in all the diodes down my left side."
        },
        {
          "id": 7086,
          "payload": "Life? Don't talk to me about life."
        },
        {
          "id": 6255,
          "payload": "You think you've got problems? What are you supposed to do if you are a manically depressed robot? No, don't try to answer that. I'm fifty thousand times more intelligent than you and even I don't know the answer. It gives me a headache just trying to think down to your level."
        }
      ]
    }
  ]
}
```

## Task 3: Refactoring

We have a business rule that if _n_ threads within a conversation have the same payload (simple string match is fine), then only the
most recent thread should be saved to main database tables. You can assume that the `threads` array is ordered with the
most recent thread first.

This rule is already implemented for you in `CompanyService.removeDuplicateThreads`, but unfortunately it was done in a suboptimal 
way. Your job is to refactor it to make it easier to maintain. There is an existing test case in `CompanyServiceTest` to help you 
ensure your refactoring doesn't break the existing business rule.

You're free to refactor in any way you'd like. This includes restructuring code/tests to different files if you see fit. While it's most
important to clean up this business rule, there are more opportunities for refactoring throughout the project. You may refactor _any_ 
part of the project as you see fit. Ultimately we just want you to showcase an ability to refactor existing code.

## Task 4: New business rules

We have two new business rules we'd like to apply when running the import. The rules are:

a. The threads that were removed during task 3 due to having the same payload as a more recent thread should be persisted 
into a conflict table of your choice (naming to be decided by you). 

For example, given the `threads` array example below, we'd expect only threads with IDs 6086 and 4591 to be persisted in the
main threads table, and ID 5071 to be persisted in the conflict threads table:

```json
[
  {
    "id": 6086,
    "payload": "Hey, can you help with?"
  },
  {
    "id": 5071,
    "payload": "Hey, can you help with"
  },
  {
    "id": 4591,
    "payload": "is a feature"
  }
]
```

b. If a company sign-up date is within the first 10 months of 2018, then it should only be saved if there aren't any conversations associated with the company.

## Task 5: New API

Add an endpoint that given a company ID will show company information in the following format:
```json
{
  "name": "Acme Ltd",
  "conversationCount": 10,
  "mostPopularUser": 298
}
``` 
The `conversationCount` should be the number of conversations within the company.

The `mostPopularUser` should be the `userId` associated with the most conversations within the company.

## Task 6: Postmortem

Open a PR from your branch to master, and within this PR please include a postmortem summarizing your findings. How did the project go? 
What would you change if you had more time?
