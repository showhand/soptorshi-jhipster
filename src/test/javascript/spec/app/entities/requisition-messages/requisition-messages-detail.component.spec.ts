/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionMessagesDetailComponent } from 'app/entities/requisition-messages/requisition-messages-detail.component';
import { RequisitionMessages } from 'app/shared/model/requisition-messages.model';

describe('Component Tests', () => {
    describe('RequisitionMessages Management Detail Component', () => {
        let comp: RequisitionMessagesDetailComponent;
        let fixture: ComponentFixture<RequisitionMessagesDetailComponent>;
        const route = ({ data: of({ requisitionMessages: new RequisitionMessages(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionMessagesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RequisitionMessagesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RequisitionMessagesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.requisitionMessages).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
