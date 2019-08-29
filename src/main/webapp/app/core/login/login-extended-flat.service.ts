import { Injectable } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLoginModalComponent } from 'app/shared';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class LoginFlatService {
    private isOpen = false;
    constructor(private modalService: NgbModal, private router: Router) {}

    open() {
        this.router.navigate(['/login']);
    }
}
