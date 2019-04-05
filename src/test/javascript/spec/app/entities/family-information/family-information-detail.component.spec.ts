/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { FamilyInformationDetailComponent } from 'app/entities/family-information/family-information-detail.component';
import { FamilyInformation } from 'app/shared/model/family-information.model';

describe('Component Tests', () => {
    describe('FamilyInformation Management Detail Component', () => {
        let comp: FamilyInformationDetailComponent;
        let fixture: ComponentFixture<FamilyInformationDetailComponent>;
        const route = ({ data: of({ familyInformation: new FamilyInformation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [FamilyInformationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FamilyInformationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FamilyInformationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.familyInformation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
