import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { User, UserService } from 'app/core';
import { EmployeeService } from 'app/entities/employee';
import { HttpResponse } from '@angular/common/http';
import { IEmployee } from 'app/shared/model/employee.model';

@Component({
    selector: 'jhi-user-mgmt-update',
    templateUrl: './user-management-update.component.html'
})
export class UserMgmtUpdateComponent implements OnInit {
    user: User;
    languages: any[];
    authorities: any[];
    isSaving: boolean;

    constructor(
        private userService: UserService,
        private route: ActivatedRoute,
        private router: Router,
        private employeeService: EmployeeService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ user }) => {
            this.user = user.body ? user.body : user;

            if (this.user.login) {
                this.employeeService
                    .query({
                        'employeeId.equals': this.user.login
                    })
                    .subscribe((res: HttpResponse<IEmployee[]>) => {
                        const employee = res.body[0];
                        this.user.email = employee.email;
                    });
            }
        });
        this.authorities = [];
        this.userService.authorities().subscribe(authorities => {
            this.authorities = authorities;
            /*    authorities.forEach((a: any) => {
                if (a !== 'ROLE_ADMIN') {
                    this.authorities.push(a);
                }
            });*/
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.user.id !== null) {
            this.userService.update(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        } else {
            this.user.langKey = 'en';
            this.userService.create(this.user).subscribe(response => this.onSaveSuccess(response), () => this.onSaveError());
        }
    }

    private onSaveSuccess(result) {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
