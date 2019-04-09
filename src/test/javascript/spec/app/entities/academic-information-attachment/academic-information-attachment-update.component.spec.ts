/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { AcademicInformationAttachmentUpdateComponent } from 'app/entities/academic-information-attachment/academic-information-attachment-update.component';
import { AcademicInformationAttachmentService } from 'app/entities/academic-information-attachment/academic-information-attachment.service';
import { AcademicInformationAttachment } from 'app/shared/model/academic-information-attachment.model';

describe('Component Tests', () => {
    describe('AcademicInformationAttachment Management Update Component', () => {
        let comp: AcademicInformationAttachmentUpdateComponent;
        let fixture: ComponentFixture<AcademicInformationAttachmentUpdateComponent>;
        let service: AcademicInformationAttachmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [AcademicInformationAttachmentUpdateComponent]
            })
                .overrideTemplate(AcademicInformationAttachmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AcademicInformationAttachmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AcademicInformationAttachmentService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new AcademicInformationAttachment(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.academicInformationAttachment = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new AcademicInformationAttachment();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.academicInformationAttachment = entity;
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
