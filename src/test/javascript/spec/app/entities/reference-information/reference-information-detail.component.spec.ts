/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ReferenceInformationDetailComponent } from 'app/entities/reference-information/reference-information-detail.component';
import { ReferenceInformation } from 'app/shared/model/reference-information.model';

describe('Component Tests', () => {
    describe('ReferenceInformation Management Detail Component', () => {
        let comp: ReferenceInformationDetailComponent;
        let fixture: ComponentFixture<ReferenceInformationDetailComponent>;
        const route = ({ data: of({ referenceInformation: new ReferenceInformation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ReferenceInformationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ReferenceInformationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ReferenceInformationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.referenceInformation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
