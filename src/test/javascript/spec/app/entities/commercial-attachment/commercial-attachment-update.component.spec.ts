/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialAttachmentUpdateComponent } from 'app/entities/commercial-attachment/commercial-attachment-update.component';
import { CommercialAttachmentService } from 'app/entities/commercial-attachment/commercial-attachment.service';
import { CommercialAttachment } from 'app/shared/model/commercial-attachment.model';

describe('Component Tests', () => {
    describe('CommercialAttachment Management Update Component', () => {
        let comp: CommercialAttachmentUpdateComponent;
        let fixture: ComponentFixture<CommercialAttachmentUpdateComponent>;
        let service: CommercialAttachmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialAttachmentUpdateComponent]
            })
                .overrideTemplate(CommercialAttachmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommercialAttachmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialAttachmentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialAttachment(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialAttachment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new CommercialAttachment();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.commercialAttachment = entity;
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
