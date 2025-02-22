import './App.css';
import {useState} from "react";

function App() {

    const [amount, setAmount] = useState(0);
    const [date, setDate] = useState(null);
    const [type, setType] = useState(null);
    const [firstRadio, setFirstRadio] = useState(null);

    return (
        <div className="App" style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center'}}>
                <img src="./logo.png" style={{height: 25, marginRight: 10}}/>
                <h1>Bank account</h1>
            </div>
                <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center'}}>
                    <div style={{marginRight: 20}}>
                        <h4 style={{marginBottom: 0}}>Amount</h4>
                        <input type={"number"} onChange={setAmount}/>
                    </div>
                    <div style={{marginRight: 20}}>
                        <h4 style={{marginBottom: 0}}>Date</h4>
                        <input type={"date"} onChange={setDate}/>
                    </div>
                    <div style={{marginRight: 20}}>
                        <h4 style={{marginBottom: 0}}>Type</h4>
                        <label style={{marginRight: 10}}>
                            <input type={"radio"} onChange={() => setType("Enter")}
                                   checked={type == "Enter" ? true : false}/>
                            Enter
                        </label>
                        <label>
                            <input type={"radio"} onChange={() => setType("Exit")}
                                   checked={type == "Exit" ? true : false}/>
                            Exit
                        </label>
                    </div>
                    <div>
                        <button style={{marginTop: 45}}>
                            Add transaction
                        </button>
                    </div>
                </div>
            </div>
            );
            }

            export default App;
