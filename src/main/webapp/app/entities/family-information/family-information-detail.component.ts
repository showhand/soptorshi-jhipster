import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFamilyInformation } from 'app/shared/model/family-information.model';

@Component({
    selector: 'jhi-family-information-detail',
    templateUrl: './family-information-detail.component.html'
})
export class FamilyInformationDetailComponent implements OnInit {
    @Input()
    familyInformation: IFamilyInformation;
    @Output()
    showFamilyInformationSection: EventEmitter<any> = new EventEmitter();
    @Output()
    editFamilyInformation: EventEmitter<any> = new EventEmitter();

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {}
    edit() {
        this.editFamilyInformation.emit();
    }

    previousState() {
        this.showFamilyInformationSection.emit();
    }
}
