/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AcademicInformationDetailComponent } from 'app/entities/academic-information/academic-information-detail.component';
import { AcademicInformation } from 'app/shared/model/academic-information.model';

describe('Component Tests', () => {
    describe('AcademicInformation Management Detail Component', () => {
        let comp: AcademicInformationDetailComponent;
        let fixture: ComponentFixture<AcademicInformationDetailComponent>;
        const route = ({ data: of({ academicInformation: new AcademicInformation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AcademicInformationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(AcademicInformationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AcademicInformationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.academicInformation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
