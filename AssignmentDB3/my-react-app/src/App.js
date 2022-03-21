import { useState } from 'react'
function App() {
  console.log('hej')

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

      <table className="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">First</th>
            <th scope="col">Last</th>
            <th scope="col">Handle</th>
          </tr>
        </thead>
        <tbody>
          {list.map(l => {
            return (
              <tr>
                <td>{l.dust}</td>
              </tr>
            )
          })}
        </tbody>
      </table>
    </>
  );
}

export default App;