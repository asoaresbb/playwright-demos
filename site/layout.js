// Shared header and footer for every page of the fictional Acme Cloud site.
// Each page has <div id="site-header"></div> and <div id="site-footer"></div> and loads this script right after them.

const navLink = (href, label) => {
  const current = location.pathname.endsWith(href) ? ' aria-current="page"' : ""
  return `<a href="${href}"${current}>${label}</a>`
}

document.getElementById("site-header").outerHTML = `
  <header class="site-header">
    <nav aria-label="Main" class="wrap">
      <a href="index.html" class="logo"><span class="logo-mark" aria-hidden="true"></span>Acme Cloud</a>
      <div class="nav-links">
        <div class="menu">
          <button type="button" aria-haspopup="true">Solutions</button>
          <ul class="submenu">
            <li><a href="securevault.html">SecureVault<small>Encrypted rooms for contracts and board papers</small></a></li>
            <li><a href="audit-log.html">Audit Log<small>See who opened what, and when</small></a></li>
          </ul>
        </div>
        ${navLink("pricing.html", "Pricing")}
        ${navLink("customers.html", "Customers")}
      </div>
      <div class="nav-actions">
        ${navLink("app.html", "Sign in")}
        <a href="securevault.html#inquiry" class="button-link">Book a demo</a>
      </div>
    </nav>
  </header>`

document.getElementById("site-footer").outerHTML = `
  <footer class="site-footer">
    <div class="wrap">
      <p>© Acme Cloud. A fictional company, made up as the target of these tests.</p>
      <ul class="footer-links">
        <li><a href="legal.html#privacy">Privacy</a></li>
        <li><a href="legal.html#terms">Terms</a></li>
        <li><a href="legal.html#security">Security</a></li>
      </ul>
    </div>
  </footer>`
