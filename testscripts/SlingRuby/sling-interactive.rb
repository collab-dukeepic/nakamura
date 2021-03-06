#!/usr/bin/env ruby

require 'rubygems'
require 'bundler'
Bundler.setup(:default)
require 'nakamura'
require 'irb'
include SlingInterface

def sling
  $s = Sling.new
  $um = SlingUsers::UserManager.new($s)
end

module IRB
  class PIRB < Irb
    # Close stdout while we initialize irb
    def initialize
      begin
        f = File.open("/dev/null", "w")
        $stdout = f
        super
      ensure
        f.close()
        $stdout = STDOUT
      end
    end
   # Don't display last value
    def output_value
    end
  end

  def IRB.start(ap_path = nil)
    $0 = File::basename(ap_path, ".rb") if ap_path

    IRB.setup(ap_path)
    @CONF[:IRB_NAME] = 'portal'
    @CONF[:AP_NAME] = 'portal'

    if @CONF[:SCRIPT]
      pirb = PIRB.new(nil, @CONF[:SCRIPT])
    else
      pirb = PIRB.new
    end

    @CONF[:IRB_RC].call(pirb.context) if @CONF[:IRB_RC]
    @CONF[:MAIN_CONTEXT] = pirb.context

    catch(:IRB_EXIT) do
      pirb.eval_input
    end
  end
end

ARGV.unshift "--simple-prompt"

IRB.start

