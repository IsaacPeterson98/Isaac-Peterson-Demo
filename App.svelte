<script>
  // @ts-nocheck

  import { onMount } from 'svelte';

  onMount(async () => {
    getdata();
  });

  // Constants
  const PRIMARY_KEY = 'id';
  const API_URL = 'http://localhost:8080/postgressApp';

  let num = undefined;
  let address = undefined;
  let firstname = undefined;
  let lastname = undefined;
  let midint = undefined;
  let email = undefined;
  let isModalOpen = false;
  let selectedItem = null;
  let isFormValid = false;

  let validform = {
    FirstName: false,
    MidInt: false,
    LastName: false,
    Email: false,
    PhoneNumber: false,
    updatedFirstName: false,
    updatedMidInt: false,
    updatedLastName: false,
    updatedEmail: false,
    updatedPhoneNumber: false
  };

  let updatedFirstName = undefined;
  let updatedMidInt = undefined;
  let updatedLastName = undefined;
  let updatedAddress = undefined;
  let updatedEmail = undefined;
  let updatedPhoneNumber = undefined;

  let data = [];
  let columns = ['First Name', 'Middle Initial', 'Last Name', 'Address', 'Email', 'Phonenumber'];

  let searchTerm = '';
  let filteredData = data;

  let isUpdateFormValid = true;

  // Event handlers for input validation
  const validateUpdatedFirstName = (event) => {
    const value = event.target.value;
    updatedFirstName = value;
    isUpdateFormValid = checkname(value) && value !== '';
  };

  const validateUpdatedMidInt = (event) => {
    const value = event.target.value;
    updatedMidInt = value;
    isUpdateFormValid = checkmidint(value);
  };

  const validateUpdatedLastName = (event) => {
    const value = event.target.value;
    updatedLastName = value;
    isUpdateFormValid = checkname(value) && value !== '';
  };

  const validateUpdatedEmail = (event) => {
    const value = event.target.value;
    updatedEmail = value;
    isUpdateFormValid = !validateemail(value);
  };

  const validateUpdatedPhoneNumber = (event) => {
    const value = event.target.value;
    updatedPhoneNumber = value;
    isUpdateFormValid = !checkphonum(removeDashes(value));
  };

  const validateFirstName = (event) => {
    const value = event.target.value;
    firstname = value;
    isFormValid = checkname(value) && value !== '';
  };

  const validateMidInt = (event) => {
    const value = event.target.value;
    midint = value;
    isFormValid = checkmidint(value);
  };

  const validateLastName = (event) => {
    const value = event.target.value;
    lastname = value;
    isFormValid = checkname(value) && value !== '';
  };

  const validateEmail = (event) => {
    const value = event.target.value;
    email = value;
    isFormValid = !validateemail(value);
  };

  const validatePhoneNumber = (event) => {
    const value = event.target.value;
    const numericValue = value.replace(/[^0-9]/g, '');
    num = numericValue;
    isFormValid = !checkphonum(removeDashes(numericValue));
  };

  // Filter data based on search term
  function filterData() {
    filteredData = data.filter(item =>
      Object.values(item).some(value =>
        value && value.toString().toLowerCase().includes(searchTerm.toLowerCase())
      )
    );
  }

  // Update focus state and validate input for the corresponding field
  function updateFocusState(fieldName) {
    for (const key in validform) {
      validform[key] = key === fieldName;
    }

    switch (fieldName) {
      case 'FirstName':
      isFormValid = checkname(firstname) && firstname !== '';
      break;
      case 'MidInt':
        isFormValid = checkmidint(midint);
        break;
      case 'LastName':
        isFormValid = checkname(lastname) && lastname !== '';
        break;
      case 'Email':
        isFormValid = !validateemail(email);
        break;
      case 'PhoneNumber':
        isFormValid = !checkphonum(removeDashes(num));
        break;

      case 'updatedFirstName':
        isUpdateFormValid = checkname(updatedFirstName) && updatedFirstName !== '';
        break;
      case 'updatedMidInt':
        isUpdateFormValid = checkmidint(updatedMidInt);
        break;
      case 'updatedLastName':
        isUpdateFormValid = checkname(updatedLastName) && updatedLastName !== '';
        break;
      case 'updatedEmail':
        isUpdateFormValid = !validateemail(updatedEmail);
        break;
      case 'updatedPhoneNumber':
        isUpdateFormValid = !checkphonum(removeDashes(updatedPhoneNumber));
        break;
    }
  }

  // Reset input values for insert entry
  function resetvalues() {
    num = undefined;
    address = undefined;
    firstname = undefined;
    lastname = undefined;
    midint = undefined;
    email = undefined;
    searchTerm = '';
  }

  // Open modal with item data
  const openModal = (item) => {
    selectedItem = item;

    updatedFirstName = item.firstname !== null ? item.firstname : '';
    updatedMidInt = item.midint !== null ? item.midint : '';
    updatedLastName = item.lastname !== null ? item.lastname : '';
    updatedAddress = item.address !== null ? item.address : '';
    updatedEmail = item.email !== null ? item.email : '';
    updatedPhoneNumber = item.phonenumber !== null ? item.phonenumber : '';

    isModalOpen = true;
  };

  // Close the modal
  const closeModal = () => {
    selectedItem = null;
    isModalOpen = false;
  };

  // Remove dashes from the phone number
  function removeDashes(num) {
    return num ? num.replace(/[^0-9]/g, '') : '';
  }

  // Validate phone number
  function checkphonum(num) {
    if (num === '' || num === undefined) return 0;
    else if (!isFinite(num)) return 1;
    else if (num.length != 10) return 1;
    else return 0;
  }

  // Validate email
  function validateemail(email) {
    if (email === '' || email === undefined) return 0;
    else if (!(String(email).toLowerCase().match(/^\S+@\S+\.\S+$/))) return 1;
    else return 0;
  }

  // Validate name
  function checkname(first) {
    const letteronly = /^[a-zA-Z]*$/;
    return letteronly.test(first);
  }

  // Validate middle initial
  function checkmidint(mid) {
    const oneletter = /^[a-zA-Z]$/;
    return mid === '' || oneletter.test(mid) || mid === undefined;
  }

  // Fetch data from the server
  async function getdata() {
    const response = await fetch(`${API_URL}/phoneList`);
    data = await response.json();
    filteredData = data;
  }

  // Delete entry
  async function deleteentry(id) {
    await fetch(`${API_URL}/deleteentry`, {
      method: 'Delete',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        id: id
      })
    });

    getdata();
    resetvalues();
  }

  // Update entry from modal
  const updateEntryFromModal = () => {
    updateentry(
      updatedFirstName,
      updatedLastName,
      updatedMidInt,
      updatedPhoneNumber,
      updatedAddress,
      updatedEmail,
      selectedItem.id
    );
    closeModal();
  };

  // Insert entry
  async function insertentry(firstname, lastname, midint, num, address, email) {
    const insert = await fetch(`${API_URL}/insertentry`, {
      method: 'Put',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        firstname: firstname,
        lastname: lastname,
        midint: midint,
        phonenumber: num,
        address: address,
        email: email
      })
    });

    getdata();
    resetvalues();
  }

  // Update entry
  async function updateentry(firstname, lastname, midint, num, address, email, id) {
    await fetch(`${API_URL}/updateentry`, {
      method: 'PATCH',
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        firstname: firstname,
        lastname: lastname,
        midint: midint,
        phonenumber: removeDashes(num),
        address: address,
        email: email,
        id: id
      })
    });

    getdata();
    resetvalues();
  }
</script>

<h1>Isaac Peterson Phone Book Application Demo</h1>

<h2>
  <input bind:value={searchTerm} on:input={filterData(searchTerm)} placeholder="Search..." />
</h2>

<section>
  <h2>
    <input bind:value={firstname} required placeholder="First name" on:input={validateFirstName} class:invalid={validform.FirstName && !isFormValid && !!firstname} on:focus={() => updateFocusState('FirstName')} />
    <input bind:value={midint} placeholder="Middle Initial" on:input={validateMidInt} class:invalid={validform.MidInt && !isFormValid && !!midint } on:focus={() => updateFocusState('MidInt')} />
    <input bind:value={lastname} placeholder="Last Name" on:input={validateLastName} class:invalid={validform.LastName && !isFormValid && !!lastname } on:focus={() => updateFocusState('LastName')} />
    <input bind:value={address} placeholder="Address" />
    <input type="email" bind:value={email} placeholder="Email" on:input={validateEmail} class:invalid={validform.Email && !isFormValid && !!email } on:focus={() => updateFocusState('Email')} />
    <input type="tel" bind:value={num} placeholder="Phone Number" on:input={validatePhoneNumber} class:invalid={validform.PhoneNumber && !isFormValid && !!num } on:focus={() => updateFocusState('PhoneNumber')} />
    <button on:click={() => insertentry(firstname, lastname, midint, num, address, email)} class="secondary-button" disabled={!isFormValid}>
      Insert Entry
    </button>
  </h2>
    {#if validform.FirstName && !isFormValid && !!firstname}
      <p class="error-message">Please enter a valid First Name</p>
    {/if}
    {#if validform.MidInt && !isFormValid && !!midint}
      <p class="error-message">Please enter a valid Middle Initial</p>
    {/if}
    {#if validform.LastName && !isFormValid && !!lastname}
      <p class="error-message">Please enter a valid Last Name</p>
    {/if}
    {#if validform.Email && !isFormValid && !!email}
      <p class="error-message">Please enter a valid Email</p>
    {/if}
    {#if validform.PhoneNumber && !isFormValid && !!num}
      <p class="error-message">Please enter a valid Phone Number</p>
    {/if}
</section>

{#if isModalOpen}
  <!-- Modal -->
  <div class="modal">
    <div class="modal-content">

      <label>First Name: <input bind:value={updatedFirstName} on:input={validateUpdatedFirstName} class:invalid={validform.updatedFirstName && !isUpdateFormValid && !!updatedFirstName} on:focus={() => updateFocusState('updatedFirstName')} /></label>
      <label>First Name: <input bind:value={updatedFirstName} on:input={validateUpdatedFirstName} class:invalid={validform.updatedFirstName && !isUpdateFormValid && !!updatedFirstName} on:focus={() => updateFocusState('updatedFirstName')} /></label>
      <label>Middle Initial: <input bind:value={updatedMidInt} on:input={validateUpdatedMidInt} class:invalid={validform.updatedMidInt && !isUpdateFormValid && !!updatedMidInt} on:focus={() => updateFocusState('updatedMidInt')} /></label>
      <label>Last Name: <input bind:value={updatedLastName} on:input={validateUpdatedLastName} class:invalid={validform.updatedLastName && !isUpdateFormValid && !!updatedLastName} on:focus={() => updateFocusState('updatedLastName')} /></label>
      <label>Address: <input bind:value={updatedAddress} /></label>
      <label>Email: <input bind:value={updatedEmail} on:input={validateUpdatedEmail} class:invalid={validform.updatedEmail && !isUpdateFormValid && !!updatedEmail} on:focus={() => updateFocusState('updatedEmail')} /></label>
      <label>PhoneNumber: <input type="tel" bind:value={updatedPhoneNumber} placeholder="Phone Number" on:input={validateUpdatedPhoneNumber} class:invalid={validform.updatedPhoneNumber && !isUpdateFormValid && !!updatedPhoneNumber} on:focus={() => updateFocusState('updatedPhoneNumber')} inputmode="numeric" />
      
      <button on:click={updateEntryFromModal} class="secondary-button" disabled={!isUpdateFormValid}>
      Update Entry
      </button>
      
      <button on:click={closeModal}>Cancel</button>

      {#if validform.updatedFirstName && !isUpdateFormValid && !!updatedFirstName}
      <p class="error-message">Please enter a valid First Name</p>
      {/if}
      {#if validform.updatedMidInt && !isUpdateFormValid && !!updatedMidInt}
        <p class="error-message">Please enter a valid Middle Initial</p>
      {/if}
      {#if validform.updatedLastName && !isUpdateFormValid && !!updatedLastName}
        <p class="error-message">Please enter a valid Last Name</p>
      {/if}
      {#if validform.updatedEmail && !isUpdateFormValid && !!updatedEmail}
        <p class="error-message">Please enter a valid Email</p>
      {/if}
      {#if validform.updatedPhoneNumber && !isUpdateFormValid && !!updatedPhoneNumber}
        <p class="error-message">Please enter a valid Phone Number</p>
      {/if}

    </div>
  </div>
{/if}

<table>
  <thead>
    <tr>
      {#each columns as column}
        <th>{column}</th>
      {/each}
    </tr>
  </thead>
  <tbody>
    {#each filteredData as item}
      <tr>
        {#each Object.keys(item) as key}
          {#if key !== PRIMARY_KEY }
          <td>{item[key] !== null ? item[key] : ""}</td>
          {/if}
        {/each}
        <button on:click={deleteentry(item.id)}>Delete</button>
        <button on:click={openModal(item)}>Update</button>
      </tr>
    {/each}
  </tbody>
</table>

<style>
  h1 {
    text-align: center;
    color: #d1d1d1; 
    margin-bottom: 20px; /* Add some space below the heading */
  }

  /* Style for the table */
  table {
    border-collapse: collapse;
    width: 100%;
  }

  th, td {
    border: 1px solid #ddd;
    padding: 12px; /* Increase padding for better readability */
    text-align: left;
  }

  /* Style for the buttons */
  button {
    color: #fff;
    background-color: #0056b3; 
    padding: 8px 12px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-right: 3px; /* Add some space between buttons */
    margin-left: 10px;
    margin-top: 5px;
  }


  /* Style for the buttons */
  .secondary-button {
    color: #fff;
    background-color: #0056b3; 
    padding: 8px 12px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    
  }

  .modal {
  display: flex;
  justify-content: center;
  align-items: flex-start; 
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);

}


.modal-content {
  background-color: #000000; 
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  z-index: 1100;
}

  section {
    margin-top: 20px; /* Add some space above the insert entry section */
  }

 input.invalid {
    border-color: red;
  }

  .error-message {
  color: red;
  font-size: 10px; /* Adjust the font size as needed */
  margin-top: 2px; /* Adjust the margin as needed */
}


  input {
    width: 7%;
    padding: 10px;
    margin: 5px 0;
    box-sizing: border-box;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 12px;
  }
</style>
