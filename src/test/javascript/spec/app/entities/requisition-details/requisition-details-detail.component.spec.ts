/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionDetailsDetailComponent } from 'app/entities/requisition-details/requisition-details-detail.component';
import { RequisitionDetails } from 'app/shared/model/requisition-details.model';

describe('Component Tests', () => {
    describe('RequisitionDetails Management Detail Component', () => {
        let comp: RequisitionDetailsDetailComponent;
        let fixture: ComponentFixture<RequisitionDetailsDetailComponent>;
        const route = ({ data: of({ requisitionDetails: new RequisitionDetails(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionDetailsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RequisitionDetailsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RequisitionDetailsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.requisitionDetails).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
