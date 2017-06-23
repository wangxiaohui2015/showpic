package com.my.showpic.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Pager class.
 */
public class Pager extends TagSupport {
	private static final long serialVersionUID = 6421075734182665276L;
	private String url;
	private String curPageVar;
	private String pages = "10";

	public int doStartTag() throws JspException {
		try {
			ServletRequest req = this.pageContext.getRequest();

			Enumeration<?> en = req.getParameterNames();
			StringBuffer params = new StringBuffer();
			while (en.hasMoreElements()) {
				String parmName = (String) en.nextElement();
				if (!parmName.equals(this.curPageVar)) {
					String value = req.getParameter(parmName);
					value = URLEncoder.encode(value, "UTF-8");
					params.append("&amp;").append(parmName).append("=").append(value);
				}

			}

			PageSorter pageSorter = (PageSorter) req.getAttribute("pageSorter");

			if (pageSorter != null) {
				JspWriter out = this.pageContext.getOut();
				StringBuffer sb = new StringBuffer();

				if (pageSorter.getPages() == 0) {
					out.print("No datas.");
					return 1;
				}

				sb.append("Current/Total:" + pageSorter.getTargetPage() + "/" + pageSorter.getPages());

				if (pageSorter.getTargetPage() != 1)
					sb.append("&nbsp;&nbsp;<a href=\"" + this.url + "?" + this.curPageVar + "=1" + params
							+ "\">First</a>&nbsp;&nbsp;");
				else {
					sb.append("&nbsp;&nbsp;First&nbsp;&nbsp;");
				}

				if (pageSorter.getTargetPage() > 1)
					sb.append("<a href=\"" + this.url + "?" + this.curPageVar + "=" + (pageSorter.getTargetPage() - 1)
							+ params + "\">Previous</a>");
				else {
					sb.append("Previous");
				}

				if (pageSorter.getPages() < Integer.parseInt(this.pages)) {
					for (int i = 1; i <= pageSorter.getPages(); i++) {
						if (i == pageSorter.getTargetPage())
							sb.append("&nbsp;&nbsp;<b>").append(i).append("</b>");
						else {
							sb.append("&nbsp;&nbsp;<a href=\"").append(this.url).append("?" + this.curPageVar + "=")
									.append(i).append(params).append("\">").append(i).append("</a>");
						}

					}

				} else if (pageSorter.getTargetPage() <= Integer.parseInt(this.pages) / 2) {
					for (int i = 1; i <= Integer.valueOf(this.pages).intValue(); i++) {
						if (i == pageSorter.getTargetPage())
							sb.append("&nbsp;&nbsp;<b>").append(i).append("</b>");
						else {
							sb.append("&nbsp;&nbsp;<a href=\"").append(this.url).append("?" + this.curPageVar + "=")
									.append(i).append(params).append("\">").append(i).append("</a>");
						}
					}
				} else if (pageSorter.getTargetPage() <= pageSorter.getPages()) {
					int first = pageSorter.getTargetPage();
					if (pageSorter.getTargetPage() <= pageSorter.getPages()
							- Integer.valueOf(this.pages).intValue() / 2)
						first = pageSorter.getTargetPage() - Integer.valueOf(this.pages).intValue() / 2;
					else {
						first = pageSorter.getPages() - Integer.valueOf(this.pages).intValue() + 1;
					}

					for (int i = first; i < first + Integer.valueOf(this.pages).intValue(); i++) {
						if (i == pageSorter.getTargetPage())
							sb.append("&nbsp;&nbsp;<b>").append(i).append("</b>");
						else {
							sb.append("&nbsp;&nbsp;<a href=\"").append(this.url).append("?" + this.curPageVar + "=")
									.append(i).append(params).append("\">").append(i).append("</a>");
						}
					}

				}

				if (pageSorter.getTargetPage() < pageSorter.getPages())
					sb.append("&nbsp;&nbsp;<a href=\"" + this.url + "?" + this.curPageVar + "="
							+ (pageSorter.getTargetPage() + 1) + params + "\">Next</a>&nbsp;&nbsp;")
							.append("<a href=\"" + this.url + "?" + this.curPageVar + "=" + pageSorter.getPages()
									+ params + "\">Last" + "</a>");
				else {
					sb.append("&nbsp;&nbsp;Next Last");
				}
				sb.append("&nbsp;&nbsp;All:" + pageSorter.getResultsAccount() + "&nbsp;Items");
				out.print(sb.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getCurPageVar() {
		return this.curPageVar;
	}

	public void setCurPageVar(String curPageVar) {
		this.curPageVar = curPageVar;
	}
}