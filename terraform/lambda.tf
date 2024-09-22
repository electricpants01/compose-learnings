# API Gateway REST API
resource "aws_api_gateway_rest_api" "todo_api" {
  name        = "todo-app-api"
  description = "API for Todo App Lambda functions"

  tags = {
    Name        = "todo-app-api"
    Environment = "dev"
    Project     = "Todo App"
  }
}

# IAM Role for Lambda
resource "aws_iam_role" "lambda_exec_role" {
  name = "todo-app-lambda-exec-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action    = "sts:AssumeRole",
        Effect    = "Allow",
        Principal = { Service = "lambda.amazonaws.com" }
      }
    ]
  })

  tags = {
    Name        = "todo-app-lambda-exec-role"
    Environment = "dev"
    Project     = "Todo App"
  }
}

# IAM Role Policy Attachments
resource "aws_iam_role_policy_attachment" "lambda_basic_execution" {
  role       = aws_iam_role.lambda_exec_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

resource "aws_iam_role_policy_attachment" "lambda_vpc_access" {
  role       = aws_iam_role.lambda_exec_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaVPCAccessExecutionRole"
}

# Lambda Functions

## Create Todo Lambda Function
resource "aws_lambda_function" "create_todo" {
  filename         = "${path.module}/lambda/create_todo.zip"
  function_name    = "todo-app-create-function"
  role             = aws_iam_role.lambda_exec_role.arn
  handler          = "create_todo.handler"
  runtime          = "nodejs20.x"
  source_code_hash = filebase64sha256("${path.module}/lambda/create_todo.zip")

  environment {
    variables = {
      DB_HOST = aws_db_instance.todo_app_db.address
      DB_USER = var.db_username
      DB_PASS = var.db_password
      DB_NAME = var.db_name
    }
  }

  vpc_config {
    subnet_ids         = [aws_subnet.public_subnet_1.id, aws_subnet.public_subnet_2.id]
    security_group_ids = [aws_security_group.rds_sg.id]
  }

  tags = {
    Name        = "todo-app-create-function"
    Environment = "dev"
    Project     = "Todo App"
  }
}

## API Gateway Integration for Create Todo
resource "aws_api_gateway_resource" "create_todo_resource" {
  rest_api_id = aws_api_gateway_rest_api.todo_api.id
  parent_id   = aws_api_gateway_rest_api.todo_api.root_resource_id
  path_part   = "create"
}

resource "aws_api_gateway_method" "create_todo_method" {
  rest_api_id   = aws_api_gateway_rest_api.todo_api.id
  resource_id   = aws_api_gateway_resource.create_todo_resource.id
  http_method   = "POST"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "create_todo_integration" {
  rest_api_id             = aws_api_gateway_rest_api.todo_api.id
  resource_id             = aws_api_gateway_resource.create_todo_resource.id
  http_method             = aws_api_gateway_method.create_todo_method.http_method
  integration_http_method = "POST"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.create_todo.invoke_arn
}

## Read Todo Lambda Function
resource "aws_lambda_function" "read_todo" {
  filename         = "${path.module}/lambda/read_todo.zip"
  function_name    = "todo-app-read-function"
  role             = aws_iam_role.lambda_exec_role.arn
  handler          = "read_todo.handler"
  runtime          = "nodejs20.x"
  source_code_hash = filebase64sha256("${path.module}/lambda/read_todo.zip")

  environment {
    variables = {
      DB_HOST = aws_db_instance.todo_app_db.address
      DB_USER = var.db_username
      DB_PASS = var.db_password
      DB_NAME = var.db_name
    }
  }

  vpc_config {
    subnet_ids         = [aws_subnet.public_subnet_1.id, aws_subnet.public_subnet_2.id]
    security_group_ids = [aws_security_group.rds_sg.id]
  }

  tags = {
    Name        = "todo-app-read-function"
    Environment = "dev"
    Project     = "Todo App"
  }
}

## API Gateway Integration for Read Todo
resource "aws_api_gateway_resource" "read_todo_resource" {
  rest_api_id = aws_api_gateway_rest_api.todo_api.id
  parent_id   = aws_api_gateway_rest_api.todo_api.root_resource_id
  path_part   = "read"
}

resource "aws_api_gateway_method" "read_todo_method" {
  rest_api_id   = aws_api_gateway_rest_api.todo_api.id
  resource_id   = aws_api_gateway_resource.read_todo_resource.id
  http_method   = "GET"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "read_todo_integration" {
  rest_api_id             = aws_api_gateway_rest_api.todo_api.id
  resource_id             = aws_api_gateway_resource.read_todo_resource.id
  http_method             = aws_api_gateway_method.read_todo_method.http_method
  integration_http_method = "GET"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.read_todo.invoke_arn
}

## Update Todo Lambda Function
resource "aws_lambda_function" "update_todo" {
  filename         = "${path.module}/lambda/update_todo.zip"
  function_name    = "todo-app-update-function"
  role             = aws_iam_role.lambda_exec_role.arn
  handler          = "update_todo.handler"
  runtime          = "nodejs20.x"
  source_code_hash = filebase64sha256("${path.module}/lambda/update_todo.zip")

  environment {
    variables = {
      DB_HOST = aws_db_instance.todo_app_db.address
      DB_USER = var.db_username
      DB_PASS = var.db_password
      DB_NAME = var.db_name
    }
  }

  vpc_config {
    subnet_ids         = [aws_subnet.public_subnet_1.id, aws_subnet.public_subnet_2.id]
    security_group_ids = [aws_security_group.rds_sg.id]
  }

  tags = {
    Name        = "todo-app-update-function"
    Environment = "dev"
    Project     = "Todo App"
  }
}

## API Gateway Integration for Update Todo
resource "aws_api_gateway_resource" "update_todo_resource" {
  rest_api_id = aws_api_gateway_rest_api.todo_api.id
  parent_id   = aws_api_gateway_rest_api.todo_api.root_resource_id
  path_part   = "update"
}

resource "aws_api_gateway_method" "update_todo_method" {
  rest_api_id   = aws_api_gateway_rest_api.todo_api.id
  resource_id   = aws_api_gateway_resource.update_todo_resource.id
  http_method   = "PUT"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "update_todo_integration" {
  rest_api_id             = aws_api_gateway_rest_api.todo_api.id
  resource_id             = aws_api_gateway_resource.update_todo_resource.id
  http_method             = aws_api_gateway_method.update_todo_method.http_method
  integration_http_method = "PUT"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.update_todo.invoke_arn
}

## Delete Todo Lambda Function
resource "aws_lambda_function" "delete_todo" {
  filename         = "${path.module}/lambda/delete_todo.zip"
  function_name    = "todo-app-delete-function"
  role             = aws_iam_role.lambda_exec_role.arn
  handler          = "delete_todo.handler"
  runtime          = "nodejs20.x"
  source_code_hash = filebase64sha256("${path.module}/lambda/delete_todo.zip")

  environment {
    variables = {
      DB_HOST = aws_db_instance.todo_app_db.address
      DB_USER = var.db_username
      DB_PASS = var.db_password
      DB_NAME = var.db_name
    }
  }

  vpc_config {
    subnet_ids         = [aws_subnet.public_subnet_1.id, aws_subnet.public_subnet_2.id]
    security_group_ids = [aws_security_group.rds_sg.id]
  }

  tags = {
    Name        = "todo-app-delete-function"
    Environment = "dev"
    Project     = "Todo App"
  }
}

## API Gateway Integration for Delete Todo
resource "aws_api_gateway_resource" "delete_todo_resource" {
  rest_api_id = aws_api_gateway_rest_api.todo_api.id
  parent_id   = aws_api_gateway_rest_api.todo_api.root_resource_id
  path_part   = "delete"
}

resource "aws_api_gateway_method" "delete_todo_method" {
  rest_api_id   = aws_api_gateway_rest_api.todo_api.id
  resource_id   = aws_api_gateway_resource.delete_todo_resource.id
  http_method   = "DELETE"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "delete_todo_integration" {
  rest_api_id             = aws_api_gateway_rest_api.todo_api.id
  resource_id             = aws_api_gateway_resource.delete_todo_resource.id
  http_method             = aws_api_gateway_method.delete_todo_method.http_method
  integration_http_method = "DELETE"
  type                    = "AWS_PROXY"
  uri                     = aws_lambda_function.delete_todo.invoke_arn
}

# API Deployment
resource "aws_api_gateway_deployment" "todo_api_deployment" {
  depends_on = [
    aws_api_gateway_integration.create_todo_integration,
    aws_api_gateway_integration.read_todo_integration,
    aws_api_gateway_integration.update_todo_integration,
    aws_api_gateway_integration.delete_todo_integration
  ]
  rest_api_id = aws_api_gateway_rest_api.todo_api.id
  stage_name  = "dev"
}