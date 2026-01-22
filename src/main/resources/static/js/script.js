const form = document.querySelector('form');

const inputName = document.getElementById('inputName');
const inputAddress = document.getElementById('inputAddress');
const inputCity = document.getElementById('inputCity');
const inputState = document.getElementById('inputState');

const output = document.getElementById('output');
const summaryLine = document.getElementById('summaryLine');
const timeLine = document.getElementById('timeLine');

const btnAbort = document.getElementById('btnAbort');

let controller = null;

async function fetchExactTime(signal) {
    const res = await fetch('https://worldtimeapi.org/api/ip', { signal });

    if (!res.ok) {
        throw new Error(`Time API HTTP ${res.status}`);
    }

    const data = await res.json();
    return data.datetime;
}

form?.addEventListener('submit', async (e) => {
    e.preventDefault();

    output.className = 'alert alert-info mb-0';
    controller = new AbortController();

    // summary object
    const name = inputName.value.trim();
    const address = inputAddress.value.trim();
    const city = inputCity.value.trim();
    const province = inputState.value;
    const s = { name, address, city, province };

    summaryLine.innerHTML = `
        <span class="text-body">${s.name}</span>
        <span class="text-muted"> — </span>
        <span class="text-body">${s.address}</span>
        <span class="text-muted">, </span>
        <span class="text-body">${s.city}</span>
        <span class="text-muted">, </span>
        <span class="text-body">${s.province}</span>
    `;
    try {
        const exactTime = await fetchExactTime(controller.signal);
        var msgHtml = `<span class="fw-semibold">${exactTime}</span>`; 
        timeLine.innerHTML = `Submitted at: ${msgHtml}`;
    } catch (err) {
        if (err.name === 'AbortError') {
            timeLine.innerHTML = '<span class="text-muted">(cancelled)</span>';
            return;
        }   
        console.error(err);
        timeLine.innerHTML = '<span class="text-muted">(failed to fetch time)</span>';
    } finally {
        controller = null;
    }
});

btnAbort?.addEventListener('click', () => {
    controller?.abort();
    summaryLine.textContent = 'Request aborted.';
    timeLine.innerHTML = '<span class="text-muted">(cancelled)</span>';
});