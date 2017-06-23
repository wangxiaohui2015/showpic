require 'open-uri'
require 'net/http'

class ImportPic
  def initialize(category)
    @base_folder = 'C:\D\eclipse\workspace\sts\showpic\src\main\webapp'
    @sub_folder = File.join('pictures')
    @category = category
  end

  def send_http_command(params)
    uri = URI.parse('http://localhost:8080/showpic/addCategoryItem')
    response = Net::HTTP::post_form(uri, params)
    if response.body == "0" then
      puts "Succeed: #{params['name']}"
    else
      puts "Failed: params: #{params}"
    end
  end

  def parse_folder
    Dir.chdir(@base_folder)
    Dir.glob("*").each do |item_name|
      next if File.file? item_name
      params = {}
      params['name'] = item_name
      params['category'] = @category
      params['items'] = get_folder_items(File.join(@base_folder, item_name), item_name)
      send_http_command(params)
    end
  end

  def get_folder_items(file_path, item_name)
    items = ''
    Dir.chdir(file_path)
    Dir.glob("*").each do |pic_name|
      next if !File.file? pic_name
      items = File.join(@sub_folder, item_name, pic_name) + ";" + items
    end
    return items
  end
end

ImportPic.new(category).parse_folder
