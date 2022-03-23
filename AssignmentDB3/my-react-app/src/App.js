import { useState } from 'react'
function App() {

  const [list, setList] = useState([]);


  const handleOnSubmit = async (e) => {
    e.preventDefault();
    let result = await fetch(
      'http://localhost:5000/twitterPosts', {
      method: "get",
      headers: {
        'Content-Type': 'application/json'
      }
    })
    result = await result.json();
    setList(result);
    console.warn(result);

  }
  return (
    <>
      <form action="">
        <button type="submit"
          onClick={handleOnSubmit}>Press me</button>
      </form>

      <table class="table">
        <thead>
          <tr>
            <th scope="col">Id</th>
            <th scope="col">Count</th>
          </tr>
        </thead>
        <tbody>
          {list.map(element => {
            return (
              <tr>
                <td>{element._id}</td>
                <td>{element.count}</td>
              </tr>
            )
          })}
        </tbody>
      </table>
    </>
  );
}

export default App;