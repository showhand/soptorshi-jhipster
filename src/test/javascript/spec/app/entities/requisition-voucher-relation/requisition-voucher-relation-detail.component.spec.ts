/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionVoucherRelationDetailComponent } from 'app/entities/requisition-voucher-relation/requisition-voucher-relation-detail.component';
import { RequisitionVoucherRelation } from 'app/shared/model/requisition-voucher-relation.model';

describe('Component Tests', () => {
    describe('RequisitionVoucherRelation Management Detail Component', () => {
        let comp: RequisitionVoucherRelationDetailComponent;
        let fixture: ComponentFixture<RequisitionVoucherRelationDetailComponent>;
        const route = ({ data: of({ requisitionVoucherRelation: new RequisitionVoucherRelation(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionVoucherRelationDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RequisitionVoucherRelationDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RequisitionVoucherRelationDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.requisitionVoucherRelation).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
