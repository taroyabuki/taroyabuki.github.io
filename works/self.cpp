#include<iostream>
#include<string>
using namespace std;string ch(string s){string p;char t;t=34;p+=t;for(unsigned int i=0;i<s.length();++i){if(s[i]==10){t=92;p+=t;t=110;p+=t;}else if(s[i]==34){t=92;p+=t;t=34;p+=t;}else if(s[i]==92){t=92;p+=t;p+=t;}else p+=s[i];}t=34;p+=t;return p;}
int main(){string s="string p=\"#include<iostream>\\n#include<string>\\nusing namespace std;string ch(string s){string p;char t;t=34;p+=t;for(unsigned int i=0;i<s.length();++i){if(s[i]==10){t=92;p+=t;t=110;p+=t;}else if(s[i]==34){t=92;p+=t;t=34;p+=t;}else if(s[i]==92){t=92;p+=t;p+=t;}else p+=s[i];}t=34;p+=t;return p;}\\nint main(){string s=\";p+=ch(s);cout<<p<<\";\"<<\"\\n\"<<s;}\n";
string p="#include<iostream>\n#include<string>\nusing namespace std;string ch(string s){string p;char t;t=34;p+=t;for(unsigned int i=0;i<s.length();++i){if(s[i]==10){t=92;p+=t;t=110;p+=t;}else if(s[i]==34){t=92;p+=t;t=34;p+=t;}else if(s[i]==92){t=92;p+=t;p+=t;}else p+=s[i];}t=34;p+=t;return p;}\nint main(){string s=";p+=ch(s);cout<<p<<";"<<"\n"<<s;}