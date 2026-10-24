(() => {
  const canvas = document.getElementById('canvas');
  const ctx = canvas.getContext('2d', { willReadFrequently: true });
  const colorPicker = document.getElementById('colorPicker');
  const colorHex = document.getElementById('colorHex');
  const brushSize = document.getElementById('brushSize');
  const sizeValue = document.getElementById('sizeValue');
  const brushPreview = document.getElementById('brushPreview');
  const emptyHint = document.getElementById('emptyHint');
  const undoBtn = document.getElementById('undoBtn');
  const redoBtn = document.getElementById('redoBtn');
  let tool = 'pen', drawing = false, start = null, snapshot = null;
  let history = [], historyIndex = -1, hasMarks = false;
  let dpr = window.devicePixelRatio || 1;
  function resizeCanvas(preserve = true) {
    const old = document.createElement('canvas');
    if (preserve && canvas.width && canvas.height) { old.width = canvas.width; old.height = canvas.height; old.getContext('2d').drawImage(canvas, 0, 0); }
    const rect = canvas.getBoundingClientRect();
    canvas.width = Math.max(1, Math.round(rect.width * dpr)); canvas.height = Math.max(1, Math.round(rect.height * dpr));
    ctx.setTransform(dpr, 0, 0, dpr, 0, 0); ctx.fillStyle = '#ffffff'; ctx.fillRect(0, 0, rect.width, rect.height);
    if (preserve && old.width && old.height) ctx.drawImage(old, 0, 0, old.width, old.height, 0, 0, rect.width, rect.height);
    document.getElementById('dimensions').textContent = `${Math.round(rect.width)} × ${Math.round(rect.height)} px`;
    if (!history.length) saveState();
  }
  function saveState() { const state = ctx.getImageData(0, 0, canvas.width, canvas.height); history = history.slice(0, historyIndex + 1); history.push(state); if (history.length > 30) history.shift(); historyIndex = history.length - 1; updateHistoryButtons(); }
  function restoreState(index) { if (index < 0 || index >= history.length) return; historyIndex = index; ctx.putImageData(history[historyIndex], 0, 0); hasMarks = historyIndex > 0; emptyHint.classList.toggle('hidden', hasMarks); updateHistoryButtons(); }
  function updateHistoryButtons() { undoBtn.disabled = historyIndex <= 0; redoBtn.disabled = historyIndex >= history.length - 1; undoBtn.style.opacity = undoBtn.disabled ? '.45' : '1'; redoBtn.style.opacity = redoBtn.disabled ? '.45' : '1'; }
  function point(e) { const r = canvas.getBoundingClientRect(); return { x: e.clientX - r.left, y: e.clientY - r.top }; }
  function style() { ctx.lineCap = 'round'; ctx.lineJoin = 'round'; ctx.strokeStyle = tool === 'eraser' ? '#ffffff' : colorPicker.value; ctx.fillStyle = ctx.strokeStyle; ctx.lineWidth = Number(brushSize.value) * (tool === 'marker' ? 2.4 : 1); ctx.globalAlpha = tool === 'marker' ? .35 : 1; }
  function drawShape(a, b) { ctx.beginPath(); if (tool === 'line') { ctx.moveTo(a.x, a.y); ctx.lineTo(b.x, b.y); ctx.stroke(); } if (tool === 'rect') ctx.strokeRect(a.x, a.y, b.x - a.x, b.y - a.y); if (tool === 'circle') { const rx = (b.x - a.x) / 2, ry = (b.y - a.y) / 2; ctx.ellipse(a.x + rx, a.y + ry, Math.abs(rx), Math.abs(ry), 0, 0, Math.PI * 2); ctx.stroke(); } }
  canvas.addEventListener('pointerdown', e => { if (e.button !== undefined && e.button !== 0) return; e.preventDefault(); canvas.setPointerCapture(e.pointerId); drawing = true; start = point(e); snapshot = ctx.getImageData(0, 0, canvas.width, canvas.height); style(); if (tool === 'pen' || tool === 'marker' || tool === 'eraser') { ctx.beginPath(); ctx.moveTo(start.x, start.y); ctx.lineTo(start.x + .01, start.y + .01); ctx.stroke(); } emptyHint.classList.add('hidden'); });
  canvas.addEventListener('pointermove', e => { if (!drawing) return; const p = point(e); style(); if (tool === 'pen' || tool === 'marker' || tool === 'eraser') { ctx.lineTo(p.x, p.y); ctx.stroke(); } else { ctx.putImageData(snapshot, 0, 0); style(); drawShape(start, p); } });
  function endDraw() { if (!drawing) return; drawing = false; ctx.globalAlpha = 1; ctx.closePath(); hasMarks = true; saveState(); snapshot = null; }
  canvas.addEventListener('pointerup', endDraw); canvas.addEventListener('pointercancel', endDraw); canvas.addEventListener('lostpointercapture', endDraw);
  document.querySelectorAll('[data-tool]').forEach(btn => btn.addEventListener('click', () => { tool = btn.dataset.tool; document.querySelectorAll('[data-tool]').forEach(b => { const active = b === btn; b.classList.toggle('active', active); b.setAttribute('aria-pressed', String(active)); }); canvas.style.cursor = tool === 'eraser' ? 'cell' : 'crosshair'; }));
  colorPicker.addEventListener('input', () => { colorHex.textContent = colorPicker.value.toUpperCase(); updateBrushPreview(); });
  document.querySelectorAll('[data-color]').forEach(btn => btn.addEventListener('click', () => { colorPicker.value = btn.dataset.color; colorPicker.dispatchEvent(new Event('input')); }));
  function updateBrushPreview() { sizeValue.textContent = `${brushSize.value} px`; const size = Math.min(30, Number(brushSize.value)); brushPreview.style.width = `${size}px`; brushPreview.style.height = `${size}px`; brushPreview.style.background = tool === 'eraser' ? '#b9c0cd' : colorPicker.value; }
  brushSize.addEventListener('input', updateBrushPreview); document.querySelectorAll('[data-tool]').forEach(btn => btn.addEventListener('click', updateBrushPreview));
  undoBtn.addEventListener('click', () => restoreState(historyIndex - 1)); redoBtn.addEventListener('click', () => restoreState(historyIndex + 1));
  document.getElementById('clearBtn').addEventListener('click', () => { ctx.globalAlpha = 1; ctx.fillStyle = '#fff'; ctx.fillRect(0, 0, canvas.width / dpr, canvas.height / dpr); hasMarks = false; emptyHint.classList.remove('hidden'); saveState(); });
  document.getElementById('whiteBtn').addEventListener('click', () => { ctx.globalAlpha = 1; ctx.fillStyle = '#fff'; ctx.fillRect(0, 0, canvas.width / dpr, canvas.height / dpr); saveState(); });
  document.getElementById('saveBtn').addEventListener('click', () => { const out = document.createElement('canvas'); out.width = canvas.width; out.height = canvas.height; const o = out.getContext('2d'); o.fillStyle = '#fff'; o.fillRect(0, 0, out.width, out.height); o.drawImage(canvas, 0, 0); const a = document.createElement('a'); a.download = `quick-draw-${new Date().toISOString().slice(0,10)}.png`; a.href = out.toDataURL('image/png'); a.click(); });
  document.addEventListener('keydown', e => { const mod = e.ctrlKey || e.metaKey; if (mod && e.key.toLowerCase() === 'z') { e.preventDefault(); restoreState(historyIndex - (e.shiftKey ? -1 : 1)); } if (mod && e.key.toLowerCase() === 'y') { e.preventDefault(); restoreState(historyIndex + 1); } });
  let resizeTimer; window.addEventListener('resize', () => { clearTimeout(resizeTimer); resizeTimer = setTimeout(() => { dpr = window.devicePixelRatio || 1; resizeCanvas(true); }, 120); });
  resizeCanvas(false); updateBrushPreview();
})();