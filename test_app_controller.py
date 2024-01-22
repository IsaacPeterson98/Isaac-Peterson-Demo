import unittest
import requests

class TestAppController(unittest.TestCase):

    BASE_URL = "http://localhost:8080/postgressApp"  

    def test_get_phoneentries(self):
        url = f"{self.BASE_URL}/phoneList"
        response = requests.get(url)

        self.assertEqual(response.status_code, 200)
        print("Phonelist:",response.status_code)

    def test_insert_entry(self):
        url = f"{self.BASE_URL}/insertentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "john@example.com",
            "phonum": "1234567890"
        }

        response = requests.put(url, json=data)

        self.assertEqual(response.status_code, 201)
        print("insert entry code:",response.status_code)

    def test_insert_entry_bad_email(self):
        url = f"{self.BASE_URL}/insertentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "johnexample.com",
            "phonum": "1234567890"
        }

        response = requests.put(url, json=data)

        self.assertEqual(response.status_code, 500)
        print("insert entry code1:",response.status_code)

    def test_insert_entry_bad_email1(self):
        url = f"{self.BASE_URL}/insertentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "johnexamplecom",
            "phonum": "1234567890"
        }

        response = requests.put(url, json=data)

        self.assertEqual(response.status_code, 500)
        print("insert bad email 1:",response.status_code)

    def test_insert_entry_bad_email2(self):
        url = f"{self.BASE_URL}/insertentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "john@examplecom",
            "phonum": "1234567890"
        }

        response = requests.put(url, json=data)

        self.assertEqual(response.status_code, 500)
        print("insert bad email 2:",response.status_code)  

    def test_insert_entry_bad_phone(self):
        url = f"{self.BASE_URL}/insertentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "john@examplecom",
            "phonum": "234567890"
        }

        response = requests.put(url, json=data)

        self.assertEqual(response.status_code, 500)
        print("insert bad phone:",response.status_code)  

    def test_delete_entry(self):
        url = f"{self.BASE_URL}/deleteentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "john@example.com",
            "phonum": "1234567890"
        }

        response = requests.delete(url, json=data)

        self.assertEqual(response.status_code, 204)
        print("delete entry code:",response.status_code)
        

    def test_update_entry(self):
        url = f"{self.BASE_URL}/updateentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "john@example.com",
            "phonum": "1234567890"
        }

        response = requests.patch(url, json=data)

        self.assertEqual(response.status_code, 200)
        print("Update entry code:",response.status_code)

    def test_update_entry_bad_email(self):
        url = f"{self.BASE_URL}/updateentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "john@examplecom",
            "phonum": "1234567890"
        }

        response = requests.patch(url, json=data)

        self.assertEqual(response.status_code, 500)
        print("Update entry bad email:",response.status_code)

    def test_update_entry_bad_email1(self):
        url = f"{self.BASE_URL}/updateentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "johnexample.com",
            "phonum": "1234567890"
        }

        response = requests.patch(url, json=data)

        self.assertEqual(response.status_code, 500)
        print("Update entry bad email 1:",response.status_code)

    def test_update_entry_bad_email2(self):
        url = f"{self.BASE_URL}/updateentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "johnexamplecom",
            "phonum": "1234567890"
        }

        response = requests.patch(url, json=data)

        self.assertEqual(response.status_code, 500)
        print("Update entry bad email2:",response.status_code)    

    def test_update_entry_bad_phone(self):
        url = f"{self.BASE_URL}/updateentry"
        data = {
            "firstname": "John",
            "lastname": "Doe",
            "midint": "M",
            "address": "123 Main St",
            "email": "johnexamplecom",
            "phonum": "234567890"
        }

        response = requests.patch(url, json=data)

        self.assertEqual(response.status_code, 500)
        print("Update entry bad phone:",response.status_code)    

if __name__ == '__main__':
    unittest.main()
