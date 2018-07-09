import { Component, OnInit, ViewChild } from '@angular/core';
import { MatProgressBar, MatButton, MatSnackBar } from '@angular/material';
import { Validators, FormGroup, FormControl } from '@angular/forms';
import { CustomValidators } from 'ng2-validation';
import { SessionService } from '../sessions.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  @ViewChild(MatProgressBar) progressBar: MatProgressBar;
  @ViewChild(MatButton) submitButton: MatButton;

  signupForm: FormGroup
  constructor(private router: Router, private sessionService: SessionService, private snack: MatSnackBar) {}

  ngOnInit() {
    const username = new FormControl('', Validators.required);
    const password = new FormControl('', Validators.required);
    const confirmPassword = new FormControl('', CustomValidators.equalTo(password));

    this.signupForm = new FormGroup({
      username: username,
      email: new FormControl('', [Validators.required, Validators.email]),
      password: password,
      confirmPassword: confirmPassword,
      agreed: new FormControl('', (control: FormControl) => {
        const agreed = control.value;
        if(!agreed) {
          return { agreed: true }
        }
        return null;
      })
    })
  }

  signup() {
    const signupData = this.signupForm.value;
    console.log(signupData);

    this.submitButton.disabled = true;
    this.progressBar.mode = 'indeterminate';
	
	this.sessionService.signup(signupData).subscribe(data => {        
		//localStorage.setItem('ngFuseToken', JSON.stringify(data));        
		const userStr = JSON.stringify(data);        
        JSON.parse(userStr, (key, value) => {
          if (key === 'accessToken') {
            localStorage.setItem('ngFuseToken', value);     
          }
          if (key === 'currentUser') {
            localStorage.setItem('ngFuseUser', value);     
          }
        });
		
        this.router.navigate(['dashboard']);
      }, error => {                       
          this.snack.open(error.message, 'OK');
          this.submitButton.disabled = false;
          this.progressBar.mode = 'determinate';
      })
	  
  }

}
