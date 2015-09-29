root = '../..'

class LoopbackController < ApplicationController
  skip_before_filter  :verify_authenticity_token

  def print
  	puts "PRINT"
  end

  def testPost
  	render :nothing => true
  	# @post = Post.new
  	puts "&&&&&&&&&&&&&&&&&&&&&&&& POST &&&&&&&&&&&&&&&&&&&&&&&&"
    puts params
  end

  def testGet
	puts "testGet"
  end

end
