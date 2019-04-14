import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAcademicInformation } from 'app/shared/model/academic-information.model';

@Component({
    selector: 'jhi-academic-information-detail',
    templateUrl: './academic-information-detail.component.html'
})
export class AcademicInformationDetailComponent implements OnInit {
    @Input()
    academicInformation: IAcademicInformation;
    @Output()
    showAcademicInformationSection: EventEmitter<any> = new EventEmitter();
    @Output()
    editAcademicInformation: EventEmitter<any> = new EventEmitter();

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {}

    edit() {
        this.editAcademicInformation.emit();
    }

    previousState() {
        this.showAcademicInformationSection.emit();
    }
}
