/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { RequisitionMessagesUpdateComponent } from 'app/entities/requisition-messages/requisition-messages-update.component';
import { RequisitionMessagesService } from 'app/entities/requisition-messages/requisition-messages.service';
import { RequisitionMessages } from 'app/shared/model/requisition-messages.model';

describe('Component Tests', () => {
    describe('RequisitionMessages Management Update Component', () => {
        let comp: RequisitionMessagesUpdateComponent;
        let fixture: ComponentFixture<RequisitionMessagesUpdateComponent>;
        let service: RequisitionMessagesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [RequisitionMessagesUpdateComponent]
            })
                .overrideTemplate(RequisitionMessagesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RequisitionMessagesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequisitionMessagesService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RequisitionMessages(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.requisitionMessages = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RequisitionMessages();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.requisitionMessages = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
