<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nhập Sản Phẩm Mới - Admin</title>
    <style>
        body { font-family: Arial; margin: 0; background: #f4f6f9; }

        .top-header {
            background: #fff;
            border-bottom: 1px solid #ddd;
            padding: 10px 30px;
            display: flex;
            justify-content: space-between;
        }

        .header-left {
            display: flex;
            gap: 20px;
            align-items: center;
        }

        .brand {
            font-size: 20px;
            font-weight: bold;
            text-decoration: none;
            color: #333;
        }

        .container {
            padding: 30px;
            max-width: 800px;
            margin: auto;
        }

        .form-card {
            background: white;
            padding: 30px;
            border-radius: 5px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-control {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .row {
            display: flex;
            gap: 10px;
        }

        .btn-submit {
            background: #e64a19;
            color: white;
            padding: 12px;
            border: none;
            width: 100%;
            cursor: pointer;
        }

        .btn-back {
            display: block;
            margin-top: 10px;
            text-align: center;
        }
    </style>
</head>

<body>

<div class="top-header">
    <div class="header-left">
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="brand">ADMIN</a>
    </div>
    <div>👤 ${sessionScope.currentUser.name}</div>
</div>

<div class="container">
    <div class="form-card">
        <h2 style="text-align:center; color:#e64a19;">NHẬP SẢN PHẨM MỚI</h2>

        <form action="${pageContext.request.contextPath}/admin/products/add" method="POST">

            <!-- ID -->
            <div class="form-group">
                <label>Mã sản phẩm</label>
                <input type="text" name="productId" class="form-control" value="${productId}" required>
            </div>

            <!-- NAME -->
            <div class="form-group">
                <label>Tên sản phẩm</label>
                <input type="text" name="productName" class="form-control" value="${productName}" required>
            </div>

            <!-- BRAND -->
            <div class="form-group">
                <label>Thương hiệu</label>
                <input type="text" name="brandInput" class="form-control" value="${brandInput}">
            </div>

            <!-- SUPPLIER -->
            <div class="form-group">
                <label>Nhà cung cấp</label>
                <input type="text" name="supplierInput" class="form-control" value="${supplierInput}">
            </div>

            <!-- CATEGORY -->
            <div class="form-group">
                <label>Danh mục</label>

                <div class="row">

                    <select name="parentId" class="form-control" onchange="this.form.submit()">
                        <option value="">-- chọn --</option>
                        <c:forEach var="c" items="${parentCategories}">
                            <option value="${c.id}" ${c.id == selectedParent ? 'selected' : ''}>
                                ${c.name}
                            </option>
                        </c:forEach>
                    </select>

                    <select name="childId" class="form-control" onchange="this.form.submit()">
                        <option value="">-- chọn --</option>
                        <c:forEach var="c" items="${childCategories}">
                            <option value="${c.id}" ${c.id == selectedChild ? 'selected' : ''}>
                                ${c.name}
                            </option>
                        </c:forEach>
                    </select>

                    <select name="categoryId" class="form-control"">
                        <option value="">-- chọn --</option>
                        <c:forEach var="c" items="${grandChildCategories}">
                            <option value="${c.id}" ${c.id == selectedCategory ? 'selected' : ''}>
                                ${c.name}
                            </option>
                        </c:forEach>
                    </select>

                </div>
            </div>

            <!-- DESCRIPTION -->
            <div class="form-group">
                <label>Mô tả</label>
                <textarea name="description" class="form-control">${description}</textarea>
            </div>

            <!-- SUBMIT -->
            <button type="submit" name="action" value="save" class="btn-submit">
                LƯU
            </button>

        </form>

        <c:if test="${not empty error}">
            <p style="color:red">${error}</p>
        </c:if>

        <a href="${pageContext.request.contextPath}/admin/products" class="btn-back">
            ← Quay lại
        </a>

    </div>
</div>

</body>
</html>